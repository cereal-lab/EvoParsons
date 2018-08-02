package evoparsons.psi;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import evoparsons.rmishared.BrokerClient;
import evoparsons.rmishared.BrokerInterface;
import evoparsons.rmishared.Fragment;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;

public class PSI
{
	public final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public final DateFormat timeFormat = new SimpleDateFormat("mm:ss");

	public static void main(String args[])
	{
		// make sure we were provided proper arguments		
	    if((System.getProperty("brokerhost") == null) || (System.getProperty("brokerport") == null)){
		System.out.println("Usage: java -Dbrokerhost=\"something.edu\" -Dbrokerport=\"12345\" -jar PSI.jar");
		System.exit(-1);
	    }
	    PSI psi = new PSI();
	    try {
			psi.start();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public PSI()
	{
		userInput = new Scanner(System.in);
		broker = BrokerClient.connect(System.getProperty("brokerhost"), Integer.parseInt(System.getProperty("brokerport")));
	}

	public void start() throws RemoteException
	{

		String studentLogin;
		System.out.println("Please enter your student Name: ");
		studentLogin = userInput.nextLine();
		int studentId = broker.getStudentID(studentLogin);
		log("Sent student login: " + studentLogin);

		// Main loop;
		// keep presenting new ParsonsPuzzles to student until they leave
		// TODO - add a hook so that in case of timeout or SIGKILL
		// we let the Broker know the evaluation is lost
		
		while (true)
		{
			ParsonsPuzzle puzzle = broker.getParsonsPuzzle(studentId);
			System.out.format("[PSI] Obtained puzzle #%d%n", puzzle.id);
			evaluate(studentId, puzzle);
		}
	}

	private Scanner userInput;
	private BrokerInterface broker;

	/**
	 * Temporary debugging method
	 **/
	private void log(String msg)
	{
		System.out.println("[PSI]\t" + msg);
	}

	private void evaluate(int studentId, ParsonsPuzzle puzzle) throws RemoteException
	{
		List<Fragment> fragments = puzzle.buildFragments();
		List<String> correctAnswer = 
			puzzle.program
				.stream()
				.map(lines -> String.join(System.lineSeparator(), lines))
				.collect(Collectors.toList());
		//int count = (int)fragments.stream().filter(f -> f.index >= 0).count();

		int attempts = 0;
		boolean giveUp = false;
		// Date elapsed;
		// long elapsedMillis;

		long startTime = System.currentTimeMillis();

		System.out.format("Evaluation start time: %s%n", dateFormat.format(startTime));

		List<Fragment> guess;
		boolean guessed = false;
		do
		{
			displayParsonsPuzzle(puzzle.title, puzzle.description, fragments);

			if (puzzle.id == -1) System.exit(1);
			// Debugging order printout

			System.out.format("%n Solution = ");
			IntStream.range(0, fragments.size())
				.filter(i -> fragments.get(i).distracter == null)
				.mapToObj(Integer::valueOf)
				.sorted(Comparator.comparing(i -> fragments.get(i).index))
				.forEach(i -> System.out.print(i + " "));
			// for (int i = 0; i < count; i++)
			// {
			// 	for (int orderCheck = 0; orderCheck < fragments.size(); orderCheck++)
			// 	{
			// 		if (fragments.get(orderCheck).getOriginalOrder() == i + 1)
			// 		{
			// 			System.out.print((orderCheck + 1) + " ");
			// 		}
			// 	}
			// }

			// User input validation
			System.out.format("%n%nEnter Your Solution: ");
			//int numAnswers = 0;
			//int position = 0;

			String line = userInput.nextLine();

			if (line.trim().equals("-1"))
			{
				System.out.format("%nBetter luck next time.%n%n%n%n");
				giveUp = true;
				break;
			}
			else
			{
				StringTokenizer tokenizer = new StringTokenizer(line);
				Iterable<String> iterable = 
					() -> new Iterator<String>() {

						@Override
						public boolean hasNext() {
							return tokenizer.hasMoreTokens();
						}

						@Override
						public String next() {
							return tokenizer.nextToken();
						}
					};
				try {
					List<Integer> guessLines = 
						StreamSupport.stream(iterable.spliterator(), false)
						.map(Integer::parseInt).collect(Collectors.toList());				
					guess = 
						guessLines.stream()
							.filter(i -> (i >= 0) && (i < fragments.size()))
							.map(i -> fragments.get(i))
							.collect(Collectors.toList());
					// Checking for distractors guessed and correct order
					List<Integer> guessedDistracters = 
						guessLines.stream()
							.filter(i -> (i >= 0) && (i < fragments.size()))
							.filter(i -> fragments.get(i).distracter != null)
							.collect(Collectors.toList());							
					if (!guessedDistracters.isEmpty())
					{
						System.out.print("You used the following erroneous lines : ");
						for (int i : guessedDistracters)
							System.out.print(i + " ");
					}
					System.out.println();																
					guessed = 
						guess.stream().map(f -> f.line).collect(Collectors.toList())
							.equals(correctAnswer);
					attempts++;
					if (!guessed)
					{
						System.out.format("%n%nWrong, try again%n");
					}																
				} catch (NumberFormatException e)
				{
					System.out.println("Please only enter integers!");
					continue;
				}
			}			
		} while (!guessed);

		long endTime = System.currentTimeMillis();
		long elapsedMillis = endTime - startTime;
		Date elapsed = new Date(elapsedMillis);

		// elapsed.setTimeInMillis();

		System.out.println(String.format("%nEvaluation end time: %s", dateFormat.format(endTime)));
		System.out.println(String.format("%nEvaluation elapsed time: %s", timeFormat.format(elapsed)));

		// sending back evaluation data
		broker.setParsonsEvaluation(new ParsonsEvaluation(studentId, puzzle.id, (double)attempts, elapsedMillis, giveUp, System.currentTimeMillis()));
		log("Sent evaluation data and got new puzzle");
	}

	/**
	 * Utility method to display {@link ParsonsPuzzle} to user
	 * 
	 * @param title
	 *            Title string array extracted from {@link ParsonsPuzzle}
	 * @param description
	 *            Description string array extracted from {@link ParsonsPuzzle}
	 * @param body
	 *            {@link List} of Line objects representing the body of the
	 *            programs
	 **/
	private void displayParsonsPuzzle(String title, String description, List<Fragment> body)
	{
		System.out.println(title);
		System.out.println(description);

		int b = 0;
		for (Fragment fragment: body)		 
			System.out.println(b++ + "\t" + fragment.line);

	}

}
