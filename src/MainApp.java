import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApp implements KeyboardIn {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Seats ss = new Seats();  // 좌석 배열

		ss.SeatList(false);		
		
		//Scanner sc = new Scanner(System.in);   // KeyboardIn interface에서 구현
		while(true) {
			System.out.println("관리자라면 비밀번호(1234)를, 일반 이용자라면 1이외의 숫자를 입력하세요:");
			int pw = SC.nextInt();
			SC.nextLine();  // 입력 버퍼 비우기 위함
			String str=String.valueOf(pw);
			
			if(pw==1234) {
				System.out.println("관리자 로그인 성공!");
				admin[0]=true;
				AdminMenu am = new AdminMenu();
				am.AdminMenuProcess();
			}
			else if (str.charAt(0) == '1') {
				continue;
			}
			else {   // 관리자가 아니라면 일반 이용자로 간주
				System.out.println("영화 예매 사이트 입장합니다.");
				Reservation res = new Reservation();
			}

		}
		//sa= sc.next();      // 빈칸 만나면 이전 내용만 수용, 이후 내용은 다음 입력으로 넘어감. nextLine()으로 보완 필요
		//sb=sc.nextLine();  // 빈칸 포함 문자열 입력 수용. nextLine()만 온전하게 입력 마감됨. 다른 Scanner들은 불완전한 입력 마감.
		//ia=sc.nextInt();    // nextInt() 다음에 nextLine() 보완해야 다음 입력 오류 발생하지 않음
		//ba=sc.nextByte();   // nextByte() 다음에 nextLine() 보완해야 다음 입력 오류 발생하지 않음
	
		
		//sc.close();
	}

}

interface KeyboardIn
{
	Scanner SC = new Scanner(System.in);
	boolean[][] seats = new boolean[8][6];  // 좌석 정보는 모두 여기에
	boolean[] admin = new boolean[1];
}

class Seats implements KeyboardIn
{
	protected String reservName="src/reservations.txt";
	protected File file;
	protected boolean view;

	public void SeatList(boolean view) throws IOException
	{
		String str;
		String[] str1;
		int row, col;
		file = new File(reservName);
		if (!file.exists()) {
			System.out.println("예매 파일이 존재하지 않습니다.");
			return;
		}	
		BufferedReader br = new BufferedReader(new FileReader(file));

		while ((str=br.readLine()) != null) {
			str1 = str.split(",");
			row=str1[3].charAt(0);   // 예약 파일의 좌석번호(예: D-3)에서 D 추출
			col=str1[3].charAt(2);	 // 예약 파일의 좌석번호(예: D-3)에서 3 추출
			seats[row-65][col-49]=true;   // 'A'는 65이므로 65를 빼서 0으로 행값 입력, "1"은 49이므로 49를 빼서 0으로 열값 입력
		}
		br.close();   // 파일 닫기
		
		if (view==true) {  // 호출 시 true값이면 좌석 배치 현황 출력
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
		}  // view=true일 때
	}
}

class FileWrite
{
	public void WriteFile(String fs, String ft, String fg) throws IOException
	{
		File file = new File("src/movies.txt");
		if (!file.exists()) {
			System.out.println("파일을 생성합니다.");
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));   // file명 뒤의 true는 append하라는 의미
		if (file.canWrite() ) {
			String str = fs+","+ft+","+fg+"\n";
			bw.write(str);  // bw.append해도 계속 하나만 저장되네
			bw.flush();
		}
		bw.close();   // 파일 닫기
	}
}

class AdminMenu extends MovieList implements KeyboardIn
{
	private int menu;
	private boolean exit=false;
	//Scanner sc = new Scanner(System.in);
	
	public void AdminMenuProcess() throws IOException
	{
		System.out.println("=====================================");
		System.out.println("==========영화 정보 관리자 메뉴===========");
		System.out.println("=====================================");
		
		while(true)
		{
			//printAdminMenu(sc);   // Scanner sc 대입할 경우
			printAdminMenu();
			adminMenuExec();
			if (exit == true)
			{
				break;
			}
		}
		System.out.println("관리자 프로그램이 종료되었습니다.");
	}
	
	public void printAdminMenu()   // (Scanner sc) 대입할 경우
	{
		System.out.println("관리 항목을 선택하세요. 1.영화 등록 2.영화 조회 3.영화 삭제 4.예매 목록 5.종료");
		menu = SC.nextInt();
		SC.nextLine();  // 입력 버퍼 비우기 위함
	}
	
	public void adminMenuExec() throws IOException
	{
		switch (menu)
		{
		case 1:    // 영화 등록
			//MovieAdd ma = new MovieAdd();  // MovieAdd 클래스 호출
			MovieAdd();   // 위 클래스 호출 대신 함수 사용
			break;
		case 2:    // 영화 조회
			MovieList();	// 영화 목록 보기
			break;
		case 3:    // 영화 삭제
			MovieDelete();

			break;
		case 4:
			ReserveList();
			break;
		case 5:    // 프로그램 종료
			System.out.println("관리 프로그램을 종료합니다.");
			exit= true;
			break;
		default:
			System.out.println("항목을 잘못 선택했습니다.");
		}
	}
	
	public void MovieAdd() throws IOException
	{
		String timeStamp;
		String MovieTitle;
		String MovieGenre;
		FileWrite fw = new FileWrite();
		
		System.out.println("등록할 영화의 제목을 입력하세요:");
		MovieTitle = SC.nextLine();   // nextLine으로 해야 제목 띄어쓰기도 입력됨
		System.out.println("등록할 영화의 장르를 입력하세요:");
		MovieGenre = SC.nextLine();
		timeStamp=String.valueOf(System.currentTimeMillis());
		System.out.print("영화: "+timeStamp+", ");
		System.out.print(MovieTitle+", ");
		System.out.println(MovieGenre);
		fw.WriteFile(timeStamp, MovieTitle, MovieGenre);
		System.out.println("영화 등록에 성공했습니다!");
		MovieList();   // 영화 목록 보기
	}
	
	public void MovieDelete() throws IOException
	{
		int delNo;
		String[] str1;
		
		MovieList();	
	
		System.out.println("삭제할 영화 번호를 입력하세요:");
		delNo = SC.nextInt();
		SC.nextLine();  // 입력 버퍼 비우기 위함
		if (delNo<1 || delNo>no) {
			System.out.println("삭제할 영화 번호를 잘못 입력했습니다.");
			return;
		}
		delNo=delNo - 1;
		file.delete();   // 기존 파일 삭제하고
		file.createNewFile();  // 동일한 파일명으로 다시 만든다.
		FileWrite fw = new FileWrite();
		for (int i=0; i<al.size(); i++) {
			if (i==delNo) {
				continue;  // 삭제할 번호와 일치하면 루프를 계속 돈다.
			}
			str1 = al.get(i).split(",");
			fw.WriteFile(str1[0], str1[1], str1[2]);  // al 배열 데이터 저장
			//System.out.println(al.get(i));
		}
		System.out.println("영화를 삭제했습니다!");
		
		MovieList();
	}
	
	void ReserveList() throws IOException
	{
		// 이하 전체 예매 목록 보기
		File file = new File("src/reservations.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		String[] str1;
		int no=0;
		System.out.println("전체 예매 목록 보기");
		while( (str=br.readLine() )!= null)
		{
			str1=str.split(",");
			no++;
			System.out.println("["+no+"] 예매번호: "+str1[0]+", 영화번호: "+str1[1]+", "+"제목: "+str1[2]+", 좌석번호: "+str1[3]);
		}
		br.close();
//		for (String s: alRes)     // ArrayList a1Res에 저장한 예매 데이터 출력
//		{
//			str1=s.split(",");
//			no++;
//			System.out.println("["+no+"] 예매번호: "+str1[0]+", 영화번호: "+str1[1]+", "+"제목: "+str1[2]+", 좌석번호: "+str1[3]);
//		}
		// 이상 전체 예매 목록 보기
	}

}   // class AdminMenu



class MovieList
{
	protected String movieName="src/movies.txt";
	protected int no;
	protected File file;
	protected ArrayList<String> al;
	
	public void MovieList() throws IOException
	{
		String str;
		String[] str1;
		
		file = new File(movieName);
		if (!file.exists()) {
			System.out.println("영화 파일이 존재하지 않습니다.");
			return;
		}

		BufferedReader br = new BufferedReader(new FileReader(file));
		al = new ArrayList<String>();
		
		if (file.canRead() ) {
			while ((str=br.readLine()) != null) {
				//System.out.println(str);
				//str1 = str.split(",");
				al.add(str);
			}
		}
		no=0;
		for (String s: al)     // ArrayList a1에 저장한 영화 데이터 출력
		{
			str1=s.split(",");
			no++;
			System.out.println("["+no+"] 고유번호: "+str1[0]+", "+"제목: "+str1[1]+", 장르: "+str1[2]);
		}
		
		br.close();   // 파일 닫기	
	}
}
