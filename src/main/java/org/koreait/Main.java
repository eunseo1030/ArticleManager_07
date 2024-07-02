package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Article> articles = new ArrayList<>();
    static List<Member> members = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("==프로그램 시작==");
        makeTestData();

        int lastArticleId = 3;

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();
            if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            if (cmd.equals("exit")) {
                break;
            } else if (cmd.equals("article write")) {
                System.out.println("==게시글 작성==");
                int id = lastArticleId + 1;
                String regDate = Util.getNow();
                String updateDate = regDate;
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);

                System.out.println(id + "번 글이 생성되었습니다");
                lastArticleId++;
            } else if (cmd.equals("article list")) {
                System.out.println("==게시글 목록==");
                if (articles.size() == 0) {
                    System.out.println("아무것도 없어");
                } else {
                    System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                    for (int i = articles.size() - 1; i >= 0; i--) {
                        Article article = articles.get(i);
                        System.out.printf("  %d   /   %s   /   %s  \n", article.getId(), article.getTitle(), article.getBody());
                    }
                }
            } else if (cmd.equals("article rq")) {
                System.out.println("==게시글 목록==");
                System.out.print("검색할 제목 키워드 입력: ");
                String keyword = sc.nextLine().trim();
                boolean found = false;
                if (articles.size() == 0) {
                    System.out.println("아무것도 없어");
                } else {
                    System.out.println("  번호   /   제목   /   내용   ");
                    for (int i = articles.size() - 1; i >= 0; i--) {
                        Article article = articles.get(i);
                        if (article.getTitle().contains(keyword)) {
                            System.out.printf("  %d   /   %s   /   %s  \n", article.getId(), article.getTitle(), article.getBody());
                            found = true;
                        }
                    }
                }
                if (!found) {
                    System.out.println("해당 키워드를 포함하는 게시글이 없습니다.");
                }
            } else if (cmd.startsWith("article detail")) {
                System.out.println("==게시글 상세보기==");
                int id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = findArticleById(id);
                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                } else {
                    System.out.println("번호 : " + foundArticle.getId());
                    System.out.println("제목 : " + foundArticle.getTitle());
                    System.out.println("내용 : " + foundArticle.getBody());
                }
            } else if (cmd.startsWith("article delete")) {
                System.out.println("==게시글 삭제==");
                int id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = findArticleById(id);
                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                } else {
                    articles.remove(foundArticle);
                    System.out.println(id + "번 게시글이 삭제되었습니다");
                }
            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");
                int id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = findArticleById(id);
                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                } else {
                    System.out.println("기존 제목 : " + foundArticle.getTitle());
                    System.out.println("기존 내용 : " + foundArticle.getBody());
                    System.out.print("새 제목 : ");
                    String newTitle = sc.nextLine();
                    System.out.print("새 내용 : ");
                    String newBody = sc.nextLine();

                    foundArticle.setTitle(newTitle);
                    foundArticle.setBody(newBody);
                    foundArticle.setUpdateDate(Util.getNow());

                    System.out.println(id + "번 게시글이 수정되었습니다");
                }
            } else if (cmd.equals("member join")) {
                System.out.println("==회원 가입==");
                System.out.print("아이디: ");
                String loginId = sc.nextLine();
                System.out.print("비밀번호: ");
                String loginPw = sc.nextLine();
                System.out.print("이름: ");
                String name = sc.nextLine();

                int memberId = members.size() + 1;
                Member member = new Member(memberId, loginId, loginPw, name);
                members.add(member);

                System.out.println("회원 가입이 완료되었습니다.");
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }
        }
        System.out.println("==프로그램 종료==");
        sc.close();
    }

    private static void makeTestData() {
        System.out.println("테스트 데이터 생성");
        articles.add(new Article(1, "2023-12-12 12:12:12", "2023-12-12 12:12:12", "제목1", "내용1"));
        articles.add(new Article(2, Util.getNow(), Util.getNow(), "제목2", "내용2"));
        articles.add(new Article(3, Util.getNow(), Util.getNow(), "제목3", "내용3"));
    }

    private static Article findArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }
}

class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private String title;
    private String body;

    public Article(int id, String regDate, String updateDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

class Member {
    private int memberId;
    private String loginId;
    private String loginPw;
    private String name;

    public Member(int memberId, String loginId, String loginPw, String name) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPw() {
        return loginPw;
    }

}
