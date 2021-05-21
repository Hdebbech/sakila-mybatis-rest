package com.debbech.sakila.repositories.mybatis;

import com.debbech.sakila.rest.dto.Film;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface FilmMapper {

    public static final String FILM_QUERY = """
            <script>
            SELECT f.film_id      id,
                               title          name,
                               l.name         language,
                               rental_rate    price,
                               f.release_year releaseYear,
                               f.length duration,
                               c.name         categories,
                               r.rentals,
                               f.special_features,
                               a.actors,
                               f.description
                        FROM   film f
                               LEFT JOIN film_category j
                                      ON j.film_id = f.film_id
                               LEFT JOIN category c
                                      ON j.category_id = c.category_id
                               LEFT JOIN (SELECT f.film_id          rental_film_id,
                                                 Count(r.rental_id) rentals
                                          FROM   film f
                                                 LEFT JOIN inventory i
                                                        ON i.film_id = f.film_id
                                                 JOIN rental r
                                                   ON r.inventory_id = i.inventory_id
                                          GROUP  BY f.film_id) r
                                      ON r.rental_film_id = f.film_id
                               LEFT JOIN (SELECT f.film_id
                                                 film_id,
                                                 Group_concat(Concat(a.first_name, " ", a.last_name))
                                                 actors
                                          FROM   film f
                                                 JOIN film_actor fa
                                                   ON fa.film_id = f.film_id
                                                 LEFT JOIN actor a
                                                        ON a.actor_id = fa.actor_id
                                          GROUP  BY film_id) a
                                      ON a.film_id = f.film_id
                               LEFT JOIN language l
                                      ON l.language_id = f.language_id
                                      <if test = "_parameter.containsKey('category')">
                                            <if test = "category!=null and category!=''">where lower(c.name) like lower('%' #{category} '%') </if>
                                      </if>
                                      <if test = "_parameter.containsKey('filmId')">
                                            <if test = "filmId!=null">where f.film_id = #{filmId} </if>
                                      </if>
                        ORDER  BY id
            </script>
            """;

    @Select(FILM_QUERY)
    @Results(
            {
                    @Result(column = "special_features", property = "specialFeatures", jdbcType = JdbcType.VARCHAR, typeHandler = StringSplitTypeHandler.class),
                    @Result(column = "actors", property = "actors", jdbcType = JdbcType.VARCHAR, typeHandler = StringSplitTypeHandler.class),
                    @Result(column = "categories", property = "categories", jdbcType = JdbcType.VARCHAR, typeHandler = StringSplitTypeHandler.class)
            }
    )
    List<Film> findByCategory(@Param("category") String category);

    @Select(FILM_QUERY)
    @Results(
            {
                    @Result(column = "special_features", property = "specialFeatures", jdbcType = JdbcType.VARCHAR, typeHandler = StringSplitTypeHandler.class),
                    @Result(column = "actors", property = "actors", jdbcType = JdbcType.VARCHAR, typeHandler = StringSplitTypeHandler.class),
                    @Result(column = "categories", property = "categories", jdbcType = JdbcType.VARCHAR, typeHandler = StringSplitTypeHandler.class)
            }
    )
    Film findById(@Param("filmId") Long filmId);
}
