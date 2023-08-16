package com.example.demo.repository;

import com.example.demo.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);
}

// 서비스: 로직 처리
// 컨트롤러: 처리된 로직을 뷰로 넘겨줌
