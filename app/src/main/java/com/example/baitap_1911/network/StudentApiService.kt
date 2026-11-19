import com.example.baitap_1911.network.StudentDto
import retrofit2.http.GET

interface StudentApiService {
    @GET("students.json")
    suspend fun getStudents(): List<StudentDto>
}