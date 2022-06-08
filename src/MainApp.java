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
		
		//Scanner sc = new Scanner(System.in);
		System.out.println("관리자라면 비밀번호(1234)를 입력하세요:");
		int pw = SC.nextInt();
		if (pw==1234) {
			System.out.println("관리자 로그인 성공!");
			AdminMenu am = new AdminMenu();
		}
		else {   // 관리자가 아니라면 일반 이용자로 간주
			System.out.println("영화 예매 사이트 입장합니다.");
			Reservation res = new Reservation();
		}
		//sc.close();
	}

}

interface KeyboardIn
{
	Scanner SC = new Scanner(System.in);
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

class AdminMenu implements KeyboardIn
{
	private int menu;
	private boolean exit=false;
	//Scanner sc = new Scanner(System.in);
	
	public AdminMenu() throws IOException
	{
		System.out.println("=====================================");
		System.out.println("==========영화 정보 관리자 메뉴============");
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
		System.out.println("관리 항목을 선택하세요. 1.영화 등록 2.영화 조회 3.영화 삭제 5.종료");
		menu = SC.nextInt();
	}
	
	public void adminMenuExec() throws IOException
	{
		switch (menu)
		{
		case 1:    // 영화 등록
			MovieAdd ma = new MovieAdd();
			break;
		case 2:    // 영화 조회
			MovieView mv = new MovieView();
			break;
		case 3:    // 영화 삭제
			MovieDelete md = new MovieDelete();
			break;
		case 5:    // 프로그램 종료
			System.out.println("관리 프로그램을 종료합니다.");
			exit= true;
			break;
		default:
			System.out.println("항목을 잘못 선택했습니다.");
		}
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
		file = new File(movieName);
		if (!file.exists()) {
			System.out.println("영화 파일이 존재하지 않습니다.");
			return;
		}

		BufferedReader br = new BufferedReader(new FileReader(file));
		al = new ArrayList<String>();
		String str;
		String[] str1;
		
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

class MovieAdd extends MovieList implements KeyboardIn
{
	private String timeStamp;
	private String MovieTitle;
	private String MovieGenre;
	FileWrite fw = new FileWrite();
	
	public MovieAdd() throws IOException
	{		
		System.out.println("등록할 영화의 제목을 입력하세요:");
		SC.nextLine();  //  버퍼에 남아있는 것들을 비우기 위함
		MovieTitle = SC.nextLine();   // nextLine으로 해야 제목 띄어쓰기도 입력됨
		System.out.println("등록할 영화의 장르를 입력하세요:");
		MovieGenre = SC.next();
		timeStamp=String.valueOf(System.currentTimeMillis());
		System.out.print("영화: "+timeStamp+", ");
		System.out.print(MovieTitle+", ");
		System.out.println(MovieGenre);
		fw.WriteFile(timeStamp, MovieTitle, MovieGenre);
		System.out.println("영화 등록에 성공했습니다!");
		MovieList();   // 영화 목록 보기
	}
}

class MovieView extends MovieList
{
	MovieView() throws IOException
	{
		MovieList();	// 영화 목록 보기	
	}
}

class MovieDelete extends MovieList implements KeyboardIn
{
	public MovieDelete() throws IOException
	{
		MovieList();
		
		int delNo;
		String[] str1;		
		System.out.println("삭제할 영화 번호를 입력하세요:");
		delNo = SC.nextInt();
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
}
