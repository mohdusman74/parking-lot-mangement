package com.example.parking.repository;
import com.example.parking.entity.*;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findAllByOrderByIdAsc();
    long countByTypeAndStatus(VehicleType type, SpotStatus status);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ParkingSpot s where s.type = :type and s.status = :status order by s.id asc")
    List<ParkingSpot> findAvailableSpotsForUpdate(@Param("type") VehicleType type,
                                                  @Param("status") SpotStatus status);
}
