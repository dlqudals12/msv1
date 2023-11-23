package com.project.msv.repository;

import com.project.msv.domain.VocaWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VocaWordRepository extends JpaRepository<VocaWord, Long> {

    @Query(value = "select count(vw) from VocaWord vw where vw.voca.id = :vocaId" +
            " and vw.word1 = :word1 and vw.word2 = :word2 and vw.word3 = :word3 and vw.word4 = :word4")
    int countDuplicateVocaWord(String word1, String word2, String word3, String word4, Long vocaId);

    List<VocaWord> findVocaWordByVocaIdOrderByRegDtAsc(Long idx);
}
