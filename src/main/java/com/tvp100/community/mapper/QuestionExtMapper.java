package com.tvp100.community.mapper;

import com.tvp100.community.dto.QuestionQueryDTO;
import com.tvp100.community.mode.Question;
import com.tvp100.community.mode.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by tvp100 on 2020/4/1.
 */
public interface QuestionExtMapper {
    int incView(@Param("record") Question record);
    int incCommentCount(@Param("record") Question record);
    List<Question> selectRelated(Question question);
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);
}
