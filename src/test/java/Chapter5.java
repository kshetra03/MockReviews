import entities.Review;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Chapter5 extends TestBase {

    final String title = "Xiaomi Redmi Note 3";
    final String body = "Good hardware. The screen is huge and reproduces good colors. Watching Videos is fun on a 5.5 inch screen.";
    final String author = "Tom";
    final String email = "tom@tv.com";

    @Test
    public void shouldCreateAndGetReview() {

        Review review = new Review(title, body, author, email);

        String reviewId = createReview(review).getId();

        Review actualReview = getReview(reviewId);

        assertEquals(actualReview.getTitle(), review.getTitle());
        assertEquals(actualReview.getBody(), review.getBody());
        assertEquals(actualReview.getAuthor(), review.getAuthor());
        assertEquals(actualReview.getEmail(), review.getEmail());

    }

    private Review getReview(String reviewId) {
        return given()
                .request().with()
                    .queryParam("format", "json")
                .get(String.format("http://localhost:8080/reviews/%s", reviewId)).as(Review.class);
    }

    private Review createReview(Review review) {
        return given()
            .request().with()
                .queryParam("format", "json")
                .body(review)
        .then()
            .post("http://localhost:8080/reviews").as(Review.class);
    }

}
