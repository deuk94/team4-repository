package src.employment.recordDAO.employmentBoardScrap;


import src.employment.board.BoardDTO;
import src.employment.recordDAO.employmentBoard.ReadDAO;
import src.user.UserMain;
import src.util.Response;

import java.util.Scanner;


public class Scraper {
    public void selectDeleteTarget(Scanner sc) {
        String userInput = "";
        int userInputNumber = 0;
        boolean breakFlag = false;
        while (!breakFlag) {
            System.out.print("취소할 게시물 번호를 입력하세요 (Q. 돌아가기): ");
            userInput = sc.nextLine();
            if (userInput.equals("Q")) {
                breakFlag = true;
            } else {
                try {
                    userInputNumber = Integer.parseInt(userInput);
                    breakFlag = true;
                    deleteToMyPage(userInputNumber);
                } catch (NumberFormatException e) {
                    System.out.println("다시 입력해주세요.");
                }
            }
        }
    }

    public void deleteToMyPage(int userInputNumber) {
        ReadDAO readDAO = new ReadDAO();
        Response<BoardDTO> readResponse = readDAO.read(userInputNumber);
        if (readResponse.isSuccess()) {
            BoardDTO boardDTO = readResponse.getData();
            int bid = boardDTO.getEmploymentBoardId();
            DeleteScrapDAO deleteScrapDAO = new DeleteScrapDAO();
            Response<String> deleteResponse = deleteScrapDAO.delete(UserMain.id, bid);
            if (deleteResponse.isSuccess()) {
                System.out.println("스크랩 해제 되었습니다.");
            } else {
                System.out.println("스크랩 해제에 실패하였습니다.");
            }
        } else {
            System.out.println("채용공고가 더 이상 존재하지 않습니다.");
        }
    }
}
