package com.example.application.data;
import java.util.List;
import com.example.application.data.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    @Query("select c from Favourites c " +
            "where c.user_id = :user_id")
    List<Favourites> find(@Param("user_id") String user_id);
    
    @Query("select c from Favourites c " +
    "where c.user_id = :user_id AND c.location_id = :location_id")
    Favourites findOne(@Param("user_id") String user_id,@Param("location_id") String location_id);
    
    @Transactional
    @Modifying
    @Query("INSERT INTO Favourites f(user_id,location_id,name,latitude, longitude) values (:user_id,:location_id,:name,:lat,:longt)")
    void insert(@Param("user_id") String user_id, @Param("location_id") Integer location_id,@Param("name") String name,@Param("lat") String lat,@Param("longt") String longt);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Favourites f WHERE id =:id")
    void delete(@Param("id") Integer id);
}
