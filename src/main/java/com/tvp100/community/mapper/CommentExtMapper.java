package com.tvp100.community.mapper;

import com.tvp100.community.mode.Comment;
import com.tvp100.community.mode.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {

   //@Update @Delete @Insert
   int incCommentCount(@Param("record")Comment record);
}
