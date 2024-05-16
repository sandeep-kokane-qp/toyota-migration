package com.boot.config;

import com.boot.qp.entity.Answer;
import com.boot.qp.entity.Question;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtility {

    static String[] QUESTION_HEADERS = {"id", "survey_id", "ord_num", "q_text", "orientation", "max_answers", "min_answers", "random", "ties_allowed", "force_all", "section_id", "branch", "q_desc", "other", "required", "branch_out", "public_result", "random_section", "type", "subtype", "show_tip", "tip_link_text", "question_tip_id", "exact_min_answers", "answer_width", "width", "font_info", "answer_font_info", "enable_multiple_branch", "desc_font_info", "desc_width", "extraction_source_id", "extraction", "code", "tip_header", "display_options", "copy_source_id", "page_break", "skip_numbering", "subsection_numbering", "constant_multiplier", "numbering_location", "num_tasks", "additional_info_id", "matrix_extraction_list", "matrix_extraction_source", "weight", "enable_custom_scale", "data_prepopulation_mode", "custom_validator_class", "custom_validator_argument", "enable_ad_hoc", "question_separator", "enable_dynamic_replacement", "calculation_mode", "chart_type", "standard_profile_id", "question_group", "survey_part_id", "canvas_app_id", "create_ts", "update_ts", "cultural_marker_id", "metadata_scale_type", "report_label", "building_block_id", "measure_id", "created_by_actor_id", "updated_by_actor_id", "draft_id", "group_id"
    };

    static String[] ANSWER_HEADERS = {"id", "survey_id", "ord_num", "other", "extraction_source_id", "code", "matrix_extraction_source", "create_ts", "update_ts", "created_by_actor_id", "updated_by_actor_id", "draft_id", "q_id", "a_text", "text_box", "branch_id", "comp_id", "comp_value", "enable_quota", "quota", "quota_branch_id", "piping_text", "exclusive_option", "size", "exclude_randomize", "location", "enable_mean_calculation", "cost_index", "answer_code", "height", "scale", "chain_survey_id", "suffix", "is_default", "report_text", "attached_custom_var", "standard_profile_option_id", "generic_settings_json", "answer_group_id"
    };

    public static List<Question> excelQuestionsToList(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet questionSheet = workbook.getSheet("Query result");
            Iterator<Row> rows = questionSheet.iterator();
            List<Question> questionList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Question question = new Question();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            question.setId((int) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            question.setSurveyId((int) currentCell.getNumericCellValue());
                            break;
                        case 3:
                            question.setQText(currentCell.getStringCellValue());
                            break;
                        case 33:
                            question.setCode(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                questionList.add(question);
            }
            workbook.close();
            return questionList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static List<Answer> excelAnswersToList(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet questionSheet = workbook.getSheet("Query result");
            Iterator<Row> rows = questionSheet.iterator();
            List<Answer> answerList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Answer answer = new Answer();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            answer.setId((int) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            answer.setSurveyId((int) currentCell.getNumericCellValue());
                            break;
                        case 5:
                            answer.setCode(currentCell.getStringCellValue());
                            break;
                        case 12:
                            answer.setQId((int) currentCell.getNumericCellValue());
                            break;
                        case 13:
                            answer.setAText(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                answerList.add(answer);
            }
            workbook.close();
            return answerList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
