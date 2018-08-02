[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Compute Sides

[#description#]
This program uses the distance formula between two points to calculate the length of the sides of a triangle.
The distance formula for two points (a1, b1) and (a2, b2) is Math.sqrt((a1 - a2) * (a1 - a2) + (b1 - b2) * (b1 - b2))

[#source#]
public class ComputeSides {
public static void main(String[] args) {
    double x1, y1, x2, y2, x3, y3, side1, side2;\\nx1 = 2.1, y1 = 2.2;\\nx2 = 2.0, y2 = -5.0;\\nx3 = 4.0, y3 = -3.0;
	side1 = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));\\nside2 = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
	System.out.println("side1 = " + side1 + " side2 = " + side2);
   }\\n}
