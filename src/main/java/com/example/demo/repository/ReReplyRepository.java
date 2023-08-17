package com.example.demo.repository;

import com.example.demo.entity.ReReply;
import com.example.demo.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReReplyRepository extends JpaRepository<ReReply, Long> {

    List<ReReply> findByReply(Reply reply);

}
