package com.bit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {
	static InputStream is = null;
	static OutputStream os = null;
	static BufferedOutputStream bos = null;
	static BufferedInputStream bis = null;
	static Scanner sc = null;
	static ArrayList<Student> studentList = null;

	public static void main(String[] args) {
		/*
		 * 학생성적관리프로그램(ver 0.5.0) 과목: 국어, 영어, 수학 
		 * 1.리스트 2.보기 3.입력 4.수정 5.삭제 0.
		 * 종료 껐다가 켜도 데이터가 유지됨
		 */
		while (true) {
			sc = new Scanner(System.in);
			System.out.println("학생성적관리프로그램(ver 0.5.0)");
			System.out.println("1.리스트 2.보기 3.입력 4.수정 5.삭제 0.종료");
			try {
				int input = sc.nextInt();
				// 종료
				if (input == 0) {
					System.out.println("프로그램을 종료합니다. 감사합니다.");
					break;
				}
				// 리스트
				if (input == 1) {
					System.out.println("=================================");
					System.out.println("학번\t국어\t영어\t수학");
					studentList = read();
					for (Student stu : studentList) {
						System.out.printf("%d\t%d\t%d\t%d\r\n", stu.num, stu.kor, stu.eng, stu.math);
					}
					System.out.println("=================================");
				}
				// 상세보기
				if (input == 2) {
					System.out.print("학번을 입력하세요>");
					int num = sc.nextInt();
					studentList = read();
					int cnt = 0;
					for (Student stu : studentList) {
						if (num == stu.num) {
							System.out.println("=================================");
							int sum = sum(stu.kor, stu.eng, stu.math);
							System.out.println("학번 : " + stu.num);
							System.out.println("합계 : " + sum);
							System.out.println("평균 : " + avg(sum));
							System.out.println("전체평균 : " + totalAvg(studentList));
							System.out.println("학점 : " + grade(sum));
							System.out.println("=================================");
						}
						if(num != stu.num && cnt == studentList.size()-1) {
							System.out.println("없는 학번입니다.");
							break;
						}
						cnt++;
					}

				}
				// 입력
				if (input == 3) {
					studentList = read();
					System.out.print("학번을 입력하세요>");
					int num = sc.nextInt();
					if(isDup(studentList, num)) continue;
					try {
						System.out.print("국어 성적을 입력하세요>");
						int kor = checkNum(sc.nextInt());
						System.out.print("영어 성적을 입력하세요>");
						int eng = checkNum(sc.nextInt());
						System.out.print("수학 성적을 입력하세요>");
						int math = checkNum(sc.nextInt());
						insert(num, kor, eng, math);
						System.out.println("데이터 입력 완료!");
					} catch (NumberRangeOverException e) {
						e.printStackTrace();
					}
				}
				// 수정
				if (input == 4) {
					studentList = read();
					System.out.println("학번을 입력하세요>");
					int num = sc.nextInt();
					for (int i = 0; i < studentList.size(); i++) {
						Student stu = studentList.get(i);
						try {
							if (num == stu.num) {
								System.out.println("수정할 성적 1.국어 2.영어 3.수학");
								int select = sc.nextInt();
								if (select == 1) {
									System.out.println("국어 성적>");
									int score = checkNum(sc.nextInt());
									stu.kor = score;
									update(studentList);
									System.out.println("수정완료!");
									break;
								} else if (select == 2) {
									System.out.println("영어 성적>");
									int score = checkNum(sc.nextInt());
									stu.eng = score;
									update(studentList);
									System.out.println("수정완료!");
									break;
								} else if (select == 3) {
									System.out.println("수학 성적>");
									int score = checkNum(sc.nextInt());
									stu.math = score;
									update(studentList);
									System.out.println("수정완료!");
									break;
								} else {
									System.out.println("잘못입력하셨습니다.");
									break;
								}
							} else if (num != stu.num && i == studentList.size() - 1) {
								System.out.println("없는 학번입니다.");
								break;
							}
						} catch (NumberRangeOverException e) {
							System.out.println(e.getMessage());
							break;
						}
					}
				}
				// 삭제
				if (input == 5) {
					studentList = read();
					System.out.print("삭제할 학번을 입력하세요>");
					int num = sc.nextInt();
					for (int i = 0; i < studentList.size(); i++) {
						Student stu = studentList.get(i);
						if (num == stu.num) {
							delete(studentList, num);
							System.out.println("삭제완료!");
						} else if (num != stu.num && i == studentList.size() - 1) {
							System.out.println("없는 학번입니다.");
							break;
						}
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자만 입력 가능합니다.");
			}
		}

	}// main 끝

	private static boolean isDup(ArrayList<Student> studentList, int num) {
		for (int i = 0; i < studentList.size(); i++) {
			Student stu = studentList.get(i);
			if (num == stu.num) {
				System.out.println("이미 존재하는 학번입니다.");
				return true;
			}
		}
		return false;
	}

	public static int sum(int kor, int eng, int math) {
		return kor + eng + math;
	}

	public static double avg(int sum) {
		return sum * 100 / 3 / 100.0;
	}

	public static double totalAvg(ArrayList<Student> studentList) {
		int total = 0;
		for (int i = 0; i < studentList.size(); i++) {
			Student st = (Student) studentList.get(i);
			total += sum(st.kor, st.eng, st.math);
		}
		return total * 100 / 3 / studentList.size() / 100.0;
	}

	public static String grade(int sum) {
		if (sum / 3 > 90)
			return "A";
		else if (sum / 3 > 80)
			return "B";
		else if (sum / 3 > 70)
			return "C";
		else if (sum / 3 > 60)
			return "D";
		else
			return "F";
	}

	public static ArrayList<Student> read() {
		ArrayList<Student> studentList = new ArrayList<Student>();
		try {
			File file = new File("DataBase.bin");
			if (file.exists()) {
				Scanner sc = new Scanner(file);
				while (sc.hasNext()) {
					String msg = sc.nextLine();
					String[] data = msg.split("\\|");
					int num = Integer.parseInt(data[0]);
					int kor = Integer.parseInt(data[1]);
					int eng = Integer.parseInt(data[2]);
					int math = Integer.parseInt(data[3]);
					studentList.add(new Student(num, kor, eng, math));
				}
				studentList.sort(new StudentComparator());
				return studentList;
			}
			// Buffer를 쓸땐 꼭 close를 해줘야한다. 그렇지않으면 값이 날라감
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	public static void insert(int num, int kor, int eng, int math) {
		try {
			File file = new File("DataBase.bin");
			if (!file.exists())
				file.createNewFile();

			os = new FileOutputStream(file, true);
			bos = new BufferedOutputStream(os);

			String msg = num + "|" + kor + "|" + eng + "|" + math + "\r\n";
			for (int i = 0; i < msg.length(); i++) {
				bos.write(msg.charAt(i));
			}

			if (bos != null)
				bos.close();
			if (os != null)
				os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void update(ArrayList<Student> studentList) {
		try {
			File file = new File("DataBase.bin");
			if (!file.exists())
				file.createNewFile();

			os = new FileOutputStream(file);
			bos = new BufferedOutputStream(os);

			for (Student stu : studentList) {
				String msg = stu.num + "|" + stu.kor + "|" + stu.eng + "|" + stu.math + "\r\n";
				for (int i = 0; i < msg.length(); i++) {
					bos.write(msg.charAt(i));
				}
			}

			if (bos != null)
				bos.close();
			if (os != null)
				os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void delete(ArrayList<Student> studentList, int num) {
		try {
			File file = new File("DataBase.bin");
			if (!file.exists())
				file.createNewFile();

			os = new FileOutputStream(file);
			bos = new BufferedOutputStream(os);
			
			for(int k = 0; k < studentList.size();k++) {
				if(num == studentList.get(k).num) {
				studentList.remove(k);
				}
			}
			
			for(Student stu : studentList){
				String msg = stu.num + "|" + stu.kor + "|" + stu.eng + "|" + stu.math + "\r\n";
				for (int i = 0; i < msg.length(); i++) {
					bos.write(msg.charAt(i));
				}
			}

			if (bos != null)
				bos.close();
			if (os != null)
				os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int checkNum(int num) throws NumberRangeOverException {
		if(num > 100 || num < 0) {
			NumberRangeOverException err = new NumberRangeOverException();
			throw err;
		}
		return num;
	}

} // class 끝

class StudentComparator implements Comparator<Student> {
		@Override
		public int compare(Student a, Student b) {
			if (a.num > b.num)
				return 1;
			if (a.num < b.num)
				return -1;
			return 0;
		}
	}

class Student {
	int num, kor, eng, math;

	public Student(int num, int kor, int eng, int math) {
		this.num = num;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}
}
class NumberRangeOverException extends Exception{
	public NumberRangeOverException() {
		super("성적은 0~100사이 숫자입니다.");
	}
}
