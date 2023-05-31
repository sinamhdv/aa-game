package aa.utils;

public class Miscellaneous {
	public static double getDistance(double x1, double y1, double x2, double y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	public static double getNeedleAngle(double x1, double y1, double x2, double y2) {
		double deltaX = x1 - x2;
		double deltaY = y1 - y2;
		if (Math.abs(deltaY) <= 1e-2) return (deltaX > 0 ? 270 : 90);
		double theta = Math.toDegrees(Math.atan(Math.abs(deltaX / deltaY)));
		double result;
		if (deltaX < 0 && deltaY > 0) result = theta;
		else if (deltaX < 0 && deltaY <= 0) result = 180 - theta;
		else if (deltaX >= 0 && deltaY < 0) result = 180 + theta;
		else result = 360 - theta;
		if (result < 0) result = 0;
		if (result >= 360) result = 0;
		return result;
	}
}
