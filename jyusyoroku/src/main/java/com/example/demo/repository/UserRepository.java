package com.example.demo.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

/**
 * ユーザー情報 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM kishimoto.jyusyo WHERE NOT delete_flg = 1 ORDER BY id ASC", nativeQuery = true)
	public Page<User> findAll(Pageable pageable);

	@Query(value = "SELECT * FROM kishimoto.jyusyo WHERE NOT delete_flg = 1 AND address LIKE %:address% ORDER BY id ASC", nativeQuery = true)
	public Page<User> findByNameContaining(@Param("address") String address, Pageable pageable);
}