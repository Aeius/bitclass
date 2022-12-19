package com.bit;

import java.util.*;

public class Ex07 {

	public static void main(String[] args) {
		/*
		 * 학생성적관리프로그램(ver 0.2.0) 교과목 - java, web, framework 1.리스트 2.입력 3.상세보기 4.삭제 0.종료>
		 * 리스트에서 데이터만 - 학번, java, web, framework 
		 * 입력은 각 학번,성적 받기 
		 * 상세보기 - 합계, 평균(학생의평균/전체평균), 학점
		 * 
		 * 제출 양식에 맞춰 각 메뉴별 실행 결과 스크린샷 첨부 1차평가_홍길동.doc
		 * 
		 * 1. 동적할당(수업 내용 내 동적할당(20)), 
		 * 2. 수업 외 동적할당(15) 
		 * 3. 시작 시 총인원 입력받기(10) 
		 * 4. 기타(5)
		 * -[10] 합계 평균(소수둘째, 학생의평균/전체평균) 학점 
		 * -[5] 각 학점 합계 평균 학점 제출양식 (10) - 2
		 */
		boolean boo = true;
		Student[] studentList = new Student[0];
		while (boo) {
			System.out.println("학생성적관리프로그램(ver 0.2.0)");
			System.out.print("1.리스트 2.입력 3.상세보기 4.삭제 0.종료>");

			Scanner sc = new Scanner(System.in);
			try {
				int input = sc.nextInt();
				// 리스트
				if (input == 1) {
					for (Student st : studentList) {
						System.out.print("학번: " + st.num + " java : " + st.java + " web : " + st.web + " framework : "
								+ st.framework);
						System.out.println();
					}
				}
				// 입력
				if (input == 2) {
					System.out.printf("학번을 입력하세요>");
					try {
						int num = sc.nextInt();
						// 학번 중복 체크
						if (dup(num, studentList))
							continue;
						System.out.printf("java 성적>");
						int java = sc.nextInt();
						System.out.printf("web 성적>");
						int web = sc.nextInt();
						System.out.printf("framework 성적>");
						int framework = sc.nextInt();
						Student st = new Student(num, java, web, framework); // 인스턴스 생성

						// 동적할당
						Student[] stArr = new Student[studentList.length + 1]; // 기존 배열보다 +1 넓은 배열 생성
						System.arraycopy(studentList, 0, stArr, 0, studentList.length); // 새로운 배열에 기존 배열 깊은 복사
						stArr[studentList.length] = st; // 배열 마지막 칸에 인스턴스 추가
						studentList = stArr; // 주소값 변경으로 기존 배열 삭제, 새로운 배열 가리킴
						System.out.println(
								"입력완료! 학번: " + num + " java : " + java + " web : " + web + " framework : " + framework);
					} catch (InputMismatchException e) {
						System.out.println("숫자만 입력 가능합니다.");
						continue;
					}
				}
				// 상세보기
				if (input == 3) {
					System.out.print("학번을 입력하세요>");
					try {
						int num = sc.nextInt();
						for (Student st : studentList) {
							if (st.num == num) {
								System.out.println("합계 : " + st.sum + " 평균 : " + st.avg + "/" + totalAvg(studentList)
										+ " 학점 : " + st.grade);
							}
						}
					} catch (InputMismatchException e) {
						System.out.println("숫자만 입력 가능합니다.");
						continue;
					}
				}
				// 삭제
				if (input == 4) {
					System.out.printf("삭제할 학생의 학번을 입력하세요>");
					try {
						int num = sc.nextInt();
						if (num == 0) {
							System.out.println("취소되었습니다.");
							continue;
						}
						// 학번 유무 확인
						if (empty(num, studentList))
							continue;
						// 동적할당
						for (int removeIndex = 0; removeIndex < studentList.length; removeIndex++) {
							if (studentList[removeIndex].num == num) {
								// studnetList 에서 studentList[i]를 제거하고, 전체길이도 1개 감소
								Student[] stArr = new Student[studentList.length - 1]; // 기존배열보다 -1 작은 배열 생성
								System.arraycopy(studentList, 0, stArr, 0, removeIndex); // removeIndex번까지 우선 복사
								System.arraycopy(studentList, removeIndex + 1, stArr, removeIndex,
										studentList.length - removeIndex - 1); // removeIndex번 이후 부터마지막 -1 까지복사
								studentList = stArr;
								System.out.println("삭제완료!");
							}
						}
					} catch (InputMismatchException e) {
						System.out.println("숫자만 입력 가능합니다!");
						continue;
					}
				}
				// 종료
				if (input == 0) {
					System.out.println("프로그램을 종료합니다. 감사합니다.");
					boo = false;
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자만 입력 가능합니다.");
			}
		}
	} // main 끝

	public static double totalAvg(Student[] studentList) {
		double avgTotal = 0;
		// 모든 학생들의 평균 총합
		for (int i = 0; i < studentList.length; i++) {
			avgTotal += studentList[i].avg;
		}
		return avgTotal / studentList.length;
	}

	public static boolean dup(int num, Student[] studentList) {
		for (Student st : studentList) {
			if (st.num == num) {
				System.out.println("중복된 학번입니다!");
				return true;
			}
		}
		return false;
	}

	public static boolean empty(int num, Student[] studentList) {
		for (Student st : studentList) {
			if (st.num == num) {
				return false;
			}
		}
		System.out.println("존재하지 않는 학번입니다.");
		return true;
	}
} // class 끝

class Student {
	int num;
	int java;
	int web;
	int framework;
	double avg;
	int sum;
	String grade;

	Student(int num, int java, int web, int framework) {
		this.num = num;
		this.java = java;
		this.web = web;
		this.framework = framework;
		this.sum = this.java + this.web + this.framework;
		this.avg = (sum * 100 / 3) / 100.0;
		grade(this.avg);
	}

	void grade(double avg) {
		// 소수 첫째 자리 반올림
		int remainder = (int) (avg * 10);
		while (remainder / 10 > 1) {
			remainder %= 10;
		}
		// 평균을 정수화
		int avgInt = (int) avg;
		if (remainder >= 5) {
			avgInt += 1;
		}
		// 학점 판별
		if (100 >= avgInt && avgInt > 90) {
			this.grade = "A";
		}
		if (90 >= avgInt && avgInt > 80) {
			this.grade = "B";
		}
		if (80 >= avgInt && avgInt > 70) {
			this.grade = "C";
		}
		if (70 >= avgInt && avgInt > 60) {
			this.grade = "D";
		}
		if (60 >= avgInt) {
			this.grade = "F";
		}
	}

}
