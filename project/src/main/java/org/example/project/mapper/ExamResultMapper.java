package org.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.project.model.ExamAnswer;
import org.example.project.model.ExamResult;

@Mapper
public interface ExamResultMapper {

    int insertExamResult(ExamResult result);

    int insertExamAnswer(ExamAnswer answer);

    int updateExamResultScore(@Param("resultId") Long resultId, @Param("score") double score);
}