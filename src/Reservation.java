import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Scanner;

public class Reservation
{
	public Reservation() throws IOException {
	// TODO Auto-generated constructor stub
	System.out.println("영화 예약 사이트입니다");
	ReservMenu rm = new ReservMenu();
	}
}

class ReservMenu implements KeyboardIn
{
	private int menu;
	private boolean exit=false;
	//Scanner sc = new Scanner(System.in);
	
	public ReservMenu() throws IOException
	{
		System.out.println("=====================================");
		System.out.println("=========영화 예약/확인/취소 메뉴===========");
		System.out.println("=====================================");
		
		while(true)
		{
			//printReservMenu(sc);   // Scanner sc 대입할 경우
			printReservMenu();
			reservMenuExec();
			if (exit == true)
			{
				break;
			}
		}
		System.out.println("예약 프로그램이 종료되었습니다.");
	}
	
	public void printReservMenu()   // (Scanner sc) 대입할 경우
	{
		System.out.println("이용할 항목을 선택하세요. 1.영화 예매 2.예매 확인 3.예매 취소 5.종료");
		menu = SC.nextInt();
	}
	
	public void reservMenuExec() throws IOException
	{
		switch(menu)
		{
		case 1:    // 영화 예매
			ReservMovie rm = new ReservMovie();
			break;
		case 2:    // 예매 확인
			ReservView rv = new ReservView();
			break;
		case 3:    // 예매 취소
			ReservCancel rc = new ReservCancel();
			break;
		case 5:    // 프로그램 종료
			System.out.println("예매 프로그램을 종료합니다.");
			exit=true;
			break;
		default:
			System.out.println("항목을 잘못 선택했습니다.");
		}
	}

}

class ReservMovie extends MovieList implements KeyboardIn
{
	int selNo;         // 선택한 영화번호
	String seatNo;     // 좌석번호
	char seatF;        // 좌석번호 첫문자 
	char seatL;		   // 좌석번호 끝문자
	String selStamp;   // 예매한 영화 고유번호
	String selTitle;   // 예매한 영화 제목
	String timeStamp;  // 영화 예매 고유번호
	Seats ss = new Seats();
	
	public ReservMovie() throws IOException {
	// TODO Auto-generated constructor stub
		System.out.println("예매 프로그램에 들어옸습니다.");
		MovieList();
		
		String[] str1;
		System.out.println("예매할 영화 번호를 입력하세요:");
		selNo = SC.nextInt();
		SC.nextLine();  // nextInt에서 남은 엔터 값을 해소하기 위함
		if (selNo<1 || selNo>no) {
			System.out.println("예매할 영화 번호를 잘못 입력했습니다.");
			return;
		}
		selNo=selNo - 1;
		// 좌석 배치도 및 선택 루틴 설정
		String seat;

		while(true) {
			System.out.println("스크린 좌석 배치도");
//			System.out.println("A-1   A-2   A-3   A-4   A-5   A-6");
//			System.out.println("B-1   B-2   B-3   B-4   B-5   B-6");
//			System.out.println("C-1   C-2   C-3   C-4   C-5   C-6");
//			System.out.println("D-1   D-2   D-3   D-4   D-5   D-6");
//			System.out.println("E-1   E-2   E-3   E-4   E-5   E-6");
//			System.out.println("F-1   F-2   F-3   F-4   F-5   F-6");
//			System.out.println("G-1   G-2   G-3   G-4   G-5   G-6");
//			System.out.println("H-1   H-2   H-3   H-4   H-5   H-6");
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
			System.out.print("예매할 좌석 번호를 입력하세요:");
			seatNo = SC.nextLine().toUpperCase();  // 입력받은 좌석번호의 대문자 변환
			if (seatNo.length() != 3 || seatNo.charAt(1) != '-') {
				System.out.println("좌석번호를 잘못 선택했습니다.\n엔터키를 치세요.");
				SC.nextLine();   // 잠시 멈춤.
				continue;
			}
			seatF=seatNo.charAt(0);
			seatL=seatNo.charAt(2);
			if (seatF<'A' || seatF>'H' || seatL<'1' || seatL>'6') {
				System.out.println("열과 행의 번호가 잘못되었습니다.\n엔터키를 치세요.");
				SC.nextLine();   // 잠시 멈춤.
				continue;
			}
			if (seats[seatF-65][seatL-49]==true) {
				System.out.println("이미 예약된 좌석입니다.\n엔터키를 치세요.");
				SC.nextLine();   // 잠시 멈춤.
				continue;
			}
			else {
				break;
			}
		}
		ReservFileWrite fw = new ReservFileWrite();
		str1 = al.get(selNo).split(",");
		selStamp=str1[0];
		selTitle=str1[1];
		timeStamp=String.valueOf(System.currentTimeMillis());
		
		fw.ReservWriteFile(timeStamp, selStamp, selTitle, seatNo);  // 예매정보 저장

		System.out.println("예매가 완료되었습니다!");
		System.out.println("예매번호: "+timeStamp+", 영화번호: "+selStamp+", 영화제목: "+selTitle+", 좌석번호: "+seatNo);
		System.out.println();
		ss.SeatList(false);    // 예약 파일(reservations.txt)에 새로 저장된 예약 좌석을 Seats배열에 반영.
	}
}

class ReservFileWrite
{
	public void ReservWriteFile(String fr, String fs, String ft, String fp) throws IOException
	{
		File file = new File("src/reservations.txt");
		if (!file.exists()) {
			System.out.println("파일을 생성합니다.");
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));   // file명 뒤의 true는 append하라는 의미
		if (file.canWrite() ) {
			String str = fr+","+fs+","+ft+","+fp+"\n";
			bw.write(str);  // bw.append해도 계속 하나만 저장되네
			bw.flush();
		}
		bw.close();   // 파일 닫기
	}
}

class ReservList
{
	protected String reservName="src/reservations.txt";
	protected int no;
	protected File file;
	protected ArrayList<String> alRes;
	protected boolean Rcheck;
	
	public void ReservList(String rvNo) throws IOException
	{
		file = new File(reservName);
		if (!file.exists()) {
			System.out.println("예매 파일이 존재하지 않습니다.");
			return;
		}

		BufferedReader br = new BufferedReader(new FileReader(file));
		alRes = new ArrayList<String>();
		String str;
		String[] str1;
		Rcheck=false;
		no=0;
		if (file.canRead() ) {
			while ((str=br.readLine()) != null) {
				//System.out.println(str);
				str1 = str.split(",");
				if (str1[0].equals(rvNo) == true) {
					no++;
					System.out.println("["+no+"] 예매번호: "+str1[0]+", 영화번호: "+str1[1]+", "+"제목: "+str1[2]+", 좌석번호: "+str1[3]);
					Rcheck=true;   //예매내역이 존재할 떼
				}
				alRes.add(str);
			}
			if (Rcheck==false) {
				System.out.println("예매 내역이 없습니다.");
			}
		}
		System.out.println();   // 빈줄 넣기
		
//		// 이하 전체 예매 목록 보기
//		no=0;
//		System.out.println("전체 예매 목록 보기");
//		for (String s: alRes)     // ArrayList a1Res에 저장한 예매 데이터 출력
//		{
//			str1=s.split(",");
//			no++;
//			System.out.println("["+no+"] 예매번호: "+str1[0]+", 영화번호: "+str1[1]+", "+"제목: "+str1[2]+", 좌석번호: "+str1[3]);
//		}
//		// 이상 전체 예매 목록 보기
		
		br.close();   // 파일 닫기	
	}

	public boolean isRcheck() {
		return Rcheck;
	}

	public void setRcheck(boolean rcheck) {
		Rcheck = rcheck;
	}
}

class ReservView extends ReservList implements KeyboardIn
{
	ReservView() throws IOException
	{
		System.out.println("확인할 예매번호를 입력하세요.");
		SC.nextLine();
		String rvNo=SC.nextLine();
		
		ReservList(rvNo);	// 예매 목록 확인하기	
	}
}

class ReservCancel extends ReservList implements KeyboardIn
{
	Seats ss = new Seats();
	
	ReservCancel() throws IOException
	{
		String[] str1;
		System.out.println("취소할 예매번호를 입력하세요.");
		SC.nextLine();
		String rcNo=SC.nextLine();
		
		ReservList(rcNo);	// 취소할 예매 번호 보기
		
		if (super.isRcheck()==true) {
			file.delete();   // 기존 파일 삭제하고
			//file.createNewFile();  // 동일한 파일명으로 다시 만든다.
			ReservFileWrite rfw = new ReservFileWrite();
			for (int i=0; i<alRes.size(); i++) {
				str1 = alRes.get(i).split(",");
				if (str1[0].equals(rcNo)==true) {
					continue;  // 삭제할 번호와 일치하면 루프를 계속 돈다.
				}
				
				rfw.ReservWriteFile(str1[0], str1[1], str1[2], str1[3]);  // alRes 배열 데이터 저장
				//System.out.println(alRes.get(i));
			}
			System.out.println("예매를 취소하였습니다!");
			Seats ss = new Seats();   // 예약 파일(reservations.txt)에서 삭제한 좌석 내용을 Seats배열에 반영.
		}
		else {
			System.out.println("예매 취소에 실패하였습니다.");
		}
		ReservList(rcNo);	// 취소한 예매 번호로 예매 내역 다시 확인하기
	}
}