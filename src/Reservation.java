import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Scanner;  // MainApp.java의 KeyboardIn interface 구현으로 충분

public class Reservation
{
	public Reservation() throws IOException {
	// TODO Auto-generated constructor stub
		System.out.println("영화 예약 사이트입니다");
		ReservMain rm = new ReservMain();
		rm.ReservMenu();
	}
}

class ReservMain extends ReservList
{
	ReservMain() throws IOException{}
	private int menu;
	//Scanner sc = new Scanner(System.in);
	
	public void ReservMenu() throws IOException
	{
		if(exit[0]==true)
			return;
		System.out.println("=====================================");
		System.out.println("=========영화 예약/확인/취소 메뉴=========");
		System.out.println("=====================================");
		
		while(true)
		{
			//printReservMenu(sc);   // Scanner sc 대입할 경우
			printReservMenu();
			reservMenuExec();
			if (exit[0] == true)
			{
				break;
			}
		}
		System.out.println("예약 프로그램이 종료되었습니다.");
	}
	
	public void printReservMenu()   // (Scanner sc) 대입할 경우
	{
		System.out.println("이용할 항목을 선택하세요. 1.영화 예매 2.예매 확인 3.예매 취소 4.예매 변경 5.관리자모드 6.종료");
		menu = SC.nextInt();
		SC.nextLine();  // 입력 버퍼 비우기 위함
	}
	
	public void reservMenuExec() throws IOException
	{
		switch(menu)
		{
		case 1:    // 영화 예매
			ReservMovie rm = new ReservMovie();
			rm.ReserveMovie();
			break;
		case 2:    // 예매 확인
			System.out.println("확인할 예매번호를 입력하세요.");
			String rvNo=SC.nextLine();			
			ReservList(rvNo);	// 예매 목록 확인하기	
			break;
		case 3:    // 예매 취소
			ReservCancel rc = new ReservCancel();
			break;
		case 4:
			ReservModify(); // 예매 번호는 그대로 나머지만 변경
			break;
		case 5:
			if (admin[0]==true)
			{
				AdminMenu am = new AdminMenu();
				am.AdminMenuProcess();
			}
//			System.out.println("관리자라면 비밀번호(1234)를 입력하세요:");
//			int pw = SC.nextInt();
//			SC.nextLine();  // 입력 버퍼 비우기 위함
//			if (pw==1234) {
//				System.out.println("관리자 로그인 성공!");
//				AdminMenu am = new AdminMenu();
//				am.AdminMenuProcess();
//			}else {
//				System.out.println("비밀번호 틀렸습니다.");
//			}
			break;
		case 6:    // 프로그램 종료
			System.out.println("예매 프로그램을 종료합니다.");
			exit[0]=true;
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
	//Seats ss = new Seats();
	
	public void ReserveMovie() throws IOException {
	// TODO Auto-generated constructor stub

		System.out.println("예매 프로그램에 들어옸습니다.");
		String[] str1;
		
		MovieList();		
		
		System.out.println("예매할 영화 번호를 입력하세요:");
		selNo = SC.nextInt();
		SC.nextLine();  // 입력 버퍼 비우기 위함. nextInt에서 남은 엔터 값을 해소.
		if (selNo<1 || selNo>no) {
			System.out.println("예매할 영화 번호를 잘못 입력했습니다.");
			return;
		}
		selNo=selNo - 1;
		// 좌석 배치도 및 선택 루틴 설정
		while(true) {
			System.out.println("스크린 좌석 배치도");
			Seats seats = new Seats(al.get(selNo).split(",")[0]); //선택한 영화번호(selNo)를 Seats의 생성자에 넣어서 해당 영화번호의 좌석들 출력
			
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
			if (seats.seat[seatF-65][seatL-49]==true) {
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
		
		// 이하, 예약 좌석을 Seats 배열에 반영하기 위함. 영화 예약을 반복할 경우, 최근 변경 사항 즉시 반영.  
//		int row, col;
//		row=seatNo.charAt(0);
//		col=seatNo.charAt(2);
//		seats.seat[row-65][col-49]=true;   //아래 명령줄 대체. 예약파일(reservation.txt에서 배열 다시 만들 필요없이 배열의 해당항목만 변경 
		//ss.SeatList(false);    // 예약 파일(reservations.txt)에 새로 저장된 예약 좌석을 Seats배열에 반영.(이것 대신 사용해도 됨)
		// 이상, 예약 좌석을 Seats 배열에 반영하기 위함.
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

class ReservList extends ReservMovie implements KeyboardIn
{
	protected String reservName="src/reservations.txt";
	protected int no;
	protected File file;
	protected ArrayList<String> alRes;
	protected boolean Rcheck;
	
	public void ReservList(String rvNo) throws IOException
	{
		String str;
		String[] str1;
		
		file = new File(reservName);
		if (!file.exists()) {
			System.out.println("예매 파일이 존재하지 않습니다.");
			return;
		}

		BufferedReader br = new BufferedReader(new FileReader(file));
		alRes = new ArrayList<String>();
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
	
	void ReservModify() throws IOException { // 예매 변경 메소드
		System.out.print("변경할 예매번호를 입력하세요 : ");
		String rvNo=SC.nextLine();
		ReservList(rvNo); // 우선 예매확인 실행
		if( Rcheck ) {  // Rcheck가 true일때
			System.out.println("무엇을 변경하시겠습니까? 1.영화변경 2. 좌석변경");
			int select = SC.nextInt();SC.nextLine();
			switch(select) {
			case 1: // 우선 영화예매 실행
				String[] str1;
				
				MovieList();		
				System.out.println("변경할 영화 번호를 입력하세요:");
				selNo = SC.nextInt();
				SC.nextLine();  // 입력 버퍼 비우기 위함. nextInt에서 남은 엔터 값을 해소.
				if (selNo<1 || selNo>super.no) {
					System.out.println("변경할 영화 번호를 잘못 입력했습니다.");
					return;
				}
				selNo=selNo - 1;
				// 좌석 배치도 및 선택 루틴 설정
				String seat;
				while(true) {
					System.out.println("스크린 좌석 배치도");
					Seats seats = new Seats(al.get(selNo).split(",")[0]); //선택한 영화번호(selNo)를 Seats의 생성자에 넣어서 해당 영화번호의 좌석들 출력

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
					if (seats.seat[seatF-65][seatL-49]==true) {
						System.out.println("이미 예약된 좌석입니다.\n엔터키를 치세요.");
						SC.nextLine();   // 잠시 멈춤.
						continue;
					}
					else {
						break;
					}
				}
				str1 = al.get(selNo).split(",");
				selStamp=str1[0];
				selTitle=str1[1];
				BufferedWriter bw = new BufferedWriter(new FileWriter(file)); // 덮어쓰기 모드
				for(String i : alRes ){
					if( i.split(",")[0].equals(rvNo) ) { // 예매목록(alRes) 읽던중 현재예매번호(rvNo)와 같으면
						bw.write(rvNo+","+selStamp+","+selTitle+","+seatNo+"\n");//예매번호는 그대로, 나머지 변경
						bw.flush();
						System.out.println("영화가 변경되었습니다 \n 영화제목: "+selTitle+", 좌석번호: "+seatNo);
						continue;
					}
					bw.write(i+"\n");
					bw.flush();
				}
				break;
				
			case 2: //좌석 변경
				while(true) {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String str;
					while( (str=br.readLine()) !=null ) {
						if(str.split(",")[0].equals(rvNo)) {  // 예매목록들중 처음부분이 예매번호와 일치하면
							str= str.split(",")[1];
							break;
							}
					}
					System.out.println("스크린 좌석 배치도");

					Seats seats = new Seats(str); //선택한 영화번호(selNo)를 Seats의 생성자에 넣어서 해당 영화번호의 좌석들 출력
					
					System.out.print("변경할 좌석 번호를 입력하세요:");
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
					if (seats.seat[seatF-65][seatL-49]==true) {
						System.out.println("이미 예약된 좌석입니다.\n엔터키를 치세요.");
						SC.nextLine();   // 잠시 멈춤.
						continue;
					}
					else {
						break;
					}
				}
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(file)); // 덮어쓰기 모드
				for(String i : alRes ){
					String[] j = i.split(",");
					if( j[0].equals(rvNo) ) { // 예매목록(alRes) 읽던중 현재예매번호(rvNo)와 같으면
						bw2.write(rvNo+","+j[1]+","+j[2]+","+seatNo+"\n");//예매번호는 그대로, 좌석 변경
						bw2.flush();
						System.out.println("좌석이 변경되었습니다. 좌석번호: "+seatNo);
						continue;
					}
					bw2.write(i+"\n");
					bw2.flush();
				}
				break;
			}
		}
	}

	public boolean isRcheck() {
		return Rcheck;
	}

	public void setRcheck(boolean rcheck) {
		Rcheck = rcheck;
	}
}

class ReservCancel extends ReservList
{
	int row, col;
	
	ReservCancel() throws IOException
	{
		String[] str1;
		System.out.println("취소할 예매번호를 입력하세요.");
		String rcNo=SC.nextLine();
		
		ReservList(rcNo);	// 취소할 예매 번호 보기
		
		if (super.isRcheck()==true) {
			file.delete();   // 기존 파일 삭제하고
			file.createNewFile();  // 동일한 파일명으로 다시 만든다.
			ReservFileWrite rfw = new ReservFileWrite();
			for (int i=0; i<alRes.size(); i++) {
				str1 = alRes.get(i).split(",");
				if (str1[0].equals(rcNo)==true) {
					row=str1[3].charAt(0);
					col=str1[3].charAt(2);
					continue;  // 삭제할 번호와 일치하면 루프를 계속 돈다.
				}
				
				rfw.ReservWriteFile(str1[0], str1[1], str1[2], str1[3]);  // alRes 배열 데이터 저장
				//System.out.println(alRes.get(i));
			}
			System.out.println("예매를 취소하였습니다!");
			//Seats ss = new Seats();   // 예약 파일(reservations.txt)에서 삭제한 좌석 내용을 Seats배열에 반영.
		}
		else {
			System.out.println("예매 취소에 실패하였습니다.");
		}
		//ReservList(rcNo);	// 취소한 예매 번호로 예매 내역 다시 확인하기
	}
}