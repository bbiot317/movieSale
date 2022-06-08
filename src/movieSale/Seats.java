package movieSale;

public class Seats {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean[][] seats = new boolean[8][6];

		// 이하 연습 예약 데이터
		seats[0][1]=true;
		seats[3][2]=true;
		seats[5][4]=true;
		seats[7][5]=true;
		// 이상 연습 예약 데이터

		String seat;
		for (int i=0; i<seats.length;i++) {
			for (int k=0; k<seats[0].length;k++) {     // seats.length: 행의 길이, seats[0].length: 열의 길이
				seat=(char)(i+65)+"-"+(k+1);           // A:65, H: 72
				if (seats[i][k]==true) {
					System.out.print(" X \t");
				}
				else {
					System.out.print(seat+"\t");
				}

			}
			System.out.println();
		}
		
	}

}
