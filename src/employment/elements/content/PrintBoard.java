package src.employment.elements.content;


import src.employment.board.BoardCategoryDTO;
import src.employment.board.BoardDTO;
import src.employment.recordDAO.employmentBoard.ReadDAO;
import src.employment.recordDAO.employmentBoardCategory.ReadCategoryDAO;
import src.util.Response;

import java.util.List;
import java.util.ArrayList;


public class PrintBoard {
	public static int allMaxPage = 0;
	public static int allMaxPageMod = 0;
	public static int keywordMaxPage = 0;
	public static int keywordMaxPageMod = 0;
	public static int regionMaxPage = 0;
	public static int regionMaxPageMod = 0;
	public static int jobMaxPage = 0;
	public static int jobMaxPageMod = 0;
	public static int mixMaxPage = 0;
	public static int mixMaxPageMod = 0;
	private List<BoardDTO> printBoardOnePage(int pageIdx, List<BoardDTO> boardList, String pageName) {
		int listSize = boardList.size();
		switch (pageName) {
			case "printAllBoards" -> {
				allMaxPage = listSize / 10;
				allMaxPageMod = listSize % 10;
				if (allMaxPageMod != 0) {
					allMaxPage ++;
				}
			}
			case "printAllBoardsByKeyword" -> {
				keywordMaxPage = listSize / 10;
				keywordMaxPageMod = listSize % 10;
				if (keywordMaxPageMod != 0) {
					keywordMaxPage ++;
				}
			}
			case "printAllBoardsByRegionDetail" -> {
				regionMaxPage = listSize / 10;
				regionMaxPageMod = listSize % 10;
				if (regionMaxPageMod != 0) {
					regionMaxPage ++;
				}
			}
			case "printAllBoardsByJobDetail" -> {
				jobMaxPage = listSize / 10;
				jobMaxPageMod = listSize % 10;
				if (jobMaxPageMod != 0) {
					jobMaxPage ++;
				}
			}
			case "printAllBoardsByRegionDetailAndJobDetail" -> {
				mixMaxPage = listSize / 10;
				mixMaxPageMod = listSize % 10;
				if (mixMaxPageMod != 0) {
					mixMaxPage ++;
				}
			}
		}

		int startIdx = (pageIdx - 1) * 10;
		int endIdx = (pageIdx * 10) - 1;

		List<BoardDTO> page = new ArrayList<>();
		for (int i = startIdx; i <= endIdx; i++) {
			try {
				BoardDTO board = boardList.get(i);
				page.add(board);
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		return page;
	}

	public String convertCategoryIdToCategoryName(int userInputSubCategoryId) {
		String convertedName = "";
		ReadCategoryDAO readCategoryDAO = new ReadCategoryDAO();
		List<BoardCategoryDTO> boardCategoryDTOList = new ArrayList<>();
		Response<List<BoardCategoryDTO>> response = readCategoryDAO.readAllCategories();
		if (response.isSuccess()) {
			boardCategoryDTOList = response.getData();
		}
		if (boardCategoryDTOList.isEmpty()) {
			System.out.println("카테고리 리스트가 비어있기 때문에 변환할 수 없습니다.");
			// 이 경우 공백이 반환됨.
		} else {
			// 전체 리스트를 돌면서 id가 일치하는 요소를 찾아냄.
			for (BoardCategoryDTO category: boardCategoryDTOList) {
				if (userInputSubCategoryId == category.getSubCategoryId()) {
					convertedName = category.getCategoryName();
					break;
				}
			}
		}
		// 맞는게 없어도 공백이 반환됨.
		return convertedName;
	}

	public void printAllRegionList() {
		ReadCategoryDAO readCategoryDAO = new ReadCategoryDAO();
		List<BoardCategoryDTO> boardCategoryDTOList = new ArrayList<>();
		Response<List<BoardCategoryDTO>> response = readCategoryDAO.readAllCategories();
		if (response.isSuccess()) {
			boardCategoryDTOList = response.getData();
		}
		if (boardCategoryDTOList.isEmpty()) {
			System.out.println("카테고리 리스트가 비어있습니다.");
		} else {
			System.out.println("-------------------------------------------------------------");
			System.out.println(":::지역 번호 리스트:::");
			for (BoardCategoryDTO category: boardCategoryDTOList) {
				if (category.getMainCategoryId() == 1) {
					System.out.println(category.getSubCategoryId()+". "+category.getCategoryName());
				}
			}
			System.out.println("-------------------------------------------------------------");
		}
	}

	public void printAllJobList() {
		ReadCategoryDAO readCategoryDAO = new ReadCategoryDAO();
		List<BoardCategoryDTO> boardCategoryDTOList = new ArrayList<>();
		Response<List<BoardCategoryDTO>> response = readCategoryDAO.readAllCategories();
		if (response.isSuccess()) {
			boardCategoryDTOList = response.getData();
		}
		if (boardCategoryDTOList.isEmpty()) {
			System.out.println("카테고리 리스트가 비어있습니다.");
		} else {
			System.out.println("-------------------------------------------------------------");
			System.out.println(":::직무 번호 리스트:::");
			for (BoardCategoryDTO category: boardCategoryDTOList) {
				if (category.getMainCategoryId() == 2) {
					System.out.println(category.getSubCategoryId()+". "+category.getCategoryName());
				}
			}
			System.out.println("-------------------------------------------------------------");
		}
	}

	// 유저가 카테고리 관련 값을 입력하면 해석해서 DB의 형식에 맞게 category id를 반환하는 메서드.
	public int interpretUserInputCategoryValue(String userInputCategoryValue) {
		int convertedSubId = 0;
		try {
			convertedSubId = Integer.parseInt(userInputCategoryValue);
		} catch (NumberFormatException e) {
			// 이 경우 유저 입력이 문자열 입력이므로 아무런 예외 처리 없이 문자열에 대한 해석만을 진행함.
		}
		if (convertedSubId == 0) { // 유저 입력이 문자열인 경우,
			ReadCategoryDAO readCategoryDAO = new ReadCategoryDAO();
			List<BoardCategoryDTO> boardCategoryDTOList = new ArrayList<>();
			Response<List<BoardCategoryDTO>> response = readCategoryDAO.readAllCategories();
			if (response.isSuccess()) {
				boardCategoryDTOList = response.getData();
			}
			if (boardCategoryDTOList.isEmpty()) {
				System.out.println("카테고리 리스트가 비어있기 때문에 변환할 수 없습니다."); // 이 경우 0이 반환됨.
			} else {
				// 전체 리스트를 돌면서 id가 일치하는 요소를 찾아냄.
				for (BoardCategoryDTO category : boardCategoryDTOList) {
					if (userInputCategoryValue.equals(category.getCategoryName())) {
						convertedSubId = category.getSubCategoryId();
						break;
					}
				}
			}
		}
		return convertedSubId;
	}

	// 채용 공고를 조건없이 모두 보여줌
	public void printAllBoards(int pageIdx) {
		ReadDAO readDao = new ReadDAO();
		Response<List<BoardDTO>> response = readDao.readAll();
		List<BoardDTO> boardList = new ArrayList<>();
		if (response.isSuccess()) {
			boardList = response.getData();
		} else {
			System.out.println("불러오기 실패.");
		}
		if (boardList.isEmpty()) {
			System.out.println("등록된 채용 게시물이 없습니다.");
		} else {
			List<BoardDTO> onePage = printBoardOnePage(pageIdx, boardList, "printAllBoards");
			for (BoardDTO board: onePage) {
				System.out.printf("글번호: %d | "
								+ "제목: %s\n",
						board.getEmploymentBoardId(),
						board.getTitle()
				);
			}
		}
	}

	// 자세히보기 -> id로 얻어온 단일 채용 공고를 보여줌.
	public void printDetailBoard(int bid) {
		ReadDAO readDao = new ReadDAO();
		Response<BoardDTO> response = readDao.read(bid);
		BoardDTO board = response.getData();
		if (response.isSuccess()) {
			System.out.printf("""
                            글번호:\t%d
                            제목:\t%s
                            근무형태:\t%s
                            요구학력:\t%s
                            채용절차:\t%s
                            자격요건:\t%s
                            우대사항:\t%s
                            회사명:\t%s
                            지역:\t%s
                            직무:\t%s
                            """,
					board.getEmploymentBoardId(),
					board.getTitle(),
					board.getJobType(),
					board.getCareer(),
					board.getHiringProcess(),
					board.getQualifications(),
					board.getPreferred(),
					board.getCompanyName(),
					convertCategoryIdToCategoryName(board.getSubCategory1Id()),
					convertCategoryIdToCategoryName(board.getSubCategory2Id())
			);
		} else {
			System.out.println("불러오기 실패.");
		}
	}

	public boolean isContained(BoardDTO boardDTO, String keyword) {
		String regionName = convertCategoryIdToCategoryName(boardDTO.getSubCategory1Id());
		String jobName = convertCategoryIdToCategoryName(boardDTO.getSubCategory2Id());
		String fullDescription = boardDTO.getEmploymentBoardId()+
				boardDTO.getTitle()+
				boardDTO.getJobType()+
				boardDTO.getCareer()+
				boardDTO.getHiringProcess()+
				boardDTO.getQualifications()+
				boardDTO.getPreferred()+
				boardDTO.getCompanyName()+
				regionName+
				jobName;
        return fullDescription.contains(keyword);
    }

	// 키워드로 조회
	public void printAllBoardsByKeyword(int pageIdx, String keyword) {
		ReadDAO readDao = new ReadDAO();
		Response<List<BoardDTO>> response = readDao.readAll();
		List<BoardDTO> boardList = new ArrayList<>();
		if (response.isSuccess()) {
			boardList = response.getData();
		}
		if (boardList.isEmpty()) {
			System.out.println("관련된 채용 게시물이 없습니다.");
		} else {
			List<BoardDTO> filteredBoardList = boardList.stream()
					.filter(boardDTO -> isContained(boardDTO, keyword))
					.toList();
			if (filteredBoardList.isEmpty()) {
				System.out.println("관련된 채용 게시물이 없습니다.");
			} else {
				List<BoardDTO> onePage = printBoardOnePage(pageIdx, filteredBoardList, "printAllBoardsByKeyword");
				for (BoardDTO board: onePage) {
					System.out.printf("글번호: %d | "
									+ "제목: %s\n",
							board.getEmploymentBoardId(),
							board.getTitle()
					);
				}
			}
		}
	}

	// 상세 지역으로 조회.
	public void printAllBoardsByRegionDetail(int pageIdx, int subCategory1Id) {
		ReadDAO readDao = new ReadDAO();
		Response<List<BoardDTO>> response = readDao.readByRegionDetail(subCategory1Id);
		List<BoardDTO> boardList = new ArrayList<>();
		if (response.isSuccess()) {
			boardList = response.getData();
		} else {
			System.out.println("불러오기 실패.");
		}
		if (boardList.isEmpty()) {
			System.out.println("관련된 채용 게시물이 없습니다.");
		} else {
			List<BoardDTO> onePage = printBoardOnePage(pageIdx, boardList, "printAllBoardsByRegionDetail");
			for (BoardDTO board: onePage) {
				System.out.printf("글번호: %d | "
								+ "제목: %s | "
								+ "지역: %s\n",
						board.getEmploymentBoardId(),
						board.getTitle(),
						convertCategoryIdToCategoryName(board.getSubCategory1Id())
				);
			}
		}
	}

	// 상세 직무로 조회.
	public void printAllBoardsByJobDetail(int pageIdx, int subCategory2Id) {
		ReadDAO readDao = new ReadDAO();
		Response<List<BoardDTO>> response = readDao.readByJobDetail(subCategory2Id);
		List<BoardDTO> boardList = new ArrayList<>();
		if (response.isSuccess()) {
			boardList = response.getData();
		} else {
			System.out.println("불러오기 실패.");
		}
		if (boardList.isEmpty()) {
			System.out.println("관련된 채용 게시물이 없습니다.");
		} else {
			List<BoardDTO> onePage = printBoardOnePage(pageIdx, boardList, "printAllBoardsByJobDetail");
			for (BoardDTO board: onePage) {
				System.out.printf("글번호: %d | "
								+ "제목: %s | "
								+ "직무: %s\n",
						board.getEmploymentBoardId(),
						board.getTitle(),
						convertCategoryIdToCategoryName(board.getSubCategory2Id())
				);
			}
		}
	}

	// 상세 지역과 상세 직무로 조회.
	public void printAllBoardsByRegionDetailAndJobDetail(int pageIdx, int subCategory1Id, int subCategory2Id) {
		ReadDAO readDao = new ReadDAO();
		Response<List<BoardDTO>> response = readDao.readByRegionDetailAndJobDetail(subCategory1Id, subCategory2Id);
		List<BoardDTO> boardList = new ArrayList<>();
		if (response.isSuccess()) {
			boardList = response.getData();
		} else {
			System.out.println("불러오기 실패.");
		}
		if (boardList.isEmpty()) {
			System.out.println("관련된 채용 게시물이 없습니다.");
		} else {
			List<BoardDTO> onePage = printBoardOnePage(pageIdx, boardList, "printAllBoardsByRegionDetailAndJobDetail");
			for (BoardDTO board: onePage) {
				System.out.printf("글번호: %d | "
								+ "제목: %s | "
								+ "지역: %s | "
								+ "직무: %s\n",
						board.getEmploymentBoardId(),
						board.getTitle(),
						convertCategoryIdToCategoryName(board.getSubCategory1Id()),
						convertCategoryIdToCategoryName(board.getSubCategory2Id())
				);
			}
		}
	}
}
