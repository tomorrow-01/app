package com.lawyerapp.data.repository

import com.lawyerapp.data.model.Lawyer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LawyerRepository @Inject constructor() {
    
    private val mockLawyers = listOf(
        Lawyer(
            id = 1,
            name = "Luật sư Nguyễn Văn An",
            specialties = listOf("Dân sự", "Hợp đồng", "Bất động sản"),
            industries = listOf("Bất động sản", "Xây dựng", "Đầu tư"),
            city = "Hồ Chí Minh",
            rating = 4.8f,
            reviews = 127,
            experience = "8 năm",
            pricePerHour = "500.000đ/giờ",
            avatarUrl = "",
            responseTime = "15 phút",
            description = "Chuyên gia về luật dân sự và hợp đồng với hơn 8 năm kinh nghiệm. Đã xử lý thành công hơn 200 vụ việc liên quan đến bất động sản và tranh chấp hợp đồng. Tư vấn chuyên sâu về các vấn đề pháp lý phức tạp.",
            phoneNumber = "0901234567",
            isOnline = true,
            address = "Tầng 15, Tòa nhà Bitexco Financial Tower, Quận 1, TP.HCM",
            education = "Thạc sĩ Luật - Đại học Luật TP.HCM",
            languages = listOf("Tiếng Việt", "English"),
            certifications = listOf("Chứng chỉ hành nghề luật sư", "Chứng chỉ trọng tài thương mại"),
            successRate = 92.5f,
            totalCases = 245
        ),
        Lawyer(
            id = 2,
            name = "Luật sư Trần Thị Bình",
            specialties = listOf("Lao động", "Bảo hiểm", "Doanh nghiệp"),
            industries = listOf("Công nghệ", "Sản xuất", "Dịch vụ"),
            city = "Hà Nội",
            rating = 4.9f,
            reviews = 89,
            experience = "12 năm",
            pricePerHour = "700.000đ/giờ",
            avatarUrl = "",
            responseTime = "10 phút",
            description = "Chuyên gia luật lao động và bảo hiểm, tư vấn cho nhiều doanh nghiệp lớn. Có kinh nghiệm sâu rộng trong việc giải quyết tranh chấp lao động và xây dựng chính sách nhân sự phù hợp với pháp luật.",
            phoneNumber = "0907654321",
            isOnline = true,
            address = "Tầng 8, Tòa nhà Lotte Center Hanoi, Quận Ba Đình, Hà Nội",
            education = "Tiến sĩ Luật - Đại học Luật Hà Nội",
            languages = listOf("Tiếng Việt", "English", "日本語"),
            certifications = listOf("Chứng chỉ hành nghề luật sư", "Chứng chỉ tư vấn doanh nghiệp"),
            successRate = 95.2f,
            totalCases = 178
        ),
        Lawyer(
            id = 3,
            name = "Luật sư Lê Minh Cường",
            specialties = listOf("Hình sự", "Tố tụng", "An ninh mạng"),
            industries = listOf("Tài chính", "Ngân hàng", "Bảo mật"),
            city = "Đà Nẵng",
            rating = 4.7f,
            reviews = 156,
            experience = "15 năm",
            pricePerHour = "800.000đ/giờ",
            avatarUrl = "",
            responseTime = "20 phút",
            description = "Luật sư hình sự kỳ cựu với nhiều vụ án thành công. Chuyên về các vụ án kinh tế, tài chính phức tạp và tội phạm công nghệ cao. Có kinh nghiệm bào chữa trong các vụ án lớn.",
            phoneNumber = "0909876543",
            isOnline = false,
            address = "Tầng 12, Tòa nhà Indochina Riverside, Quận Hải Châu, Đà Nẵng",
            education = "Cử nhân Luật - Đại học Luật Huế, Thạc sĩ Tội phạm học",
            languages = listOf("Tiếng Việt"),
            certifications = listOf("Chứng chỉ hành nghề luật sư", "Chứng chỉ điều tra tư"),
            successRate = 88.7f,
            totalCases = 312
        ),
        Lawyer(
            id = 4,
            name = "Luật sư Phạm Thu Hà",
            specialties = listOf("Sở hữu trí tuệ", "Công nghệ", "Thương mại điện tử"),
            industries = listOf("Công nghệ", "Khởi nghiệp", "E-commerce"),
            city = "Hồ Chí Minh",
            rating = 4.9f,
            reviews = 203,
            experience = "6 năm",
            pricePerHour = "600.000đ/giờ",
            avatarUrl = "",
            responseTime = "5 phút",
            description = "Chuyên gia về luật sở hữu trí tuệ và công nghệ. Tư vấn cho nhiều startup và công ty công nghệ về bảo vệ thương hiệu, bản quyền, và tuân thủ pháp luật trong kinh doanh online.",
            phoneNumber = "0905432109",
            isOnline = true,
            address = "Tầng 20, Tòa nhà Landmark 81, Quận Bình Thạnh, TP.HCM",
            education = "Thạc sĩ Luật Kinh tế - Đại học Kinh tế TP.HCM",
            languages = listOf("Tiếng Việt", "English", "한국어"),
            certifications = listOf("Chứng chỉ hành nghề luật sư", "Chứng chỉ IP Specialist"),
            successRate = 96.8f,
            totalCases = 134
        ),
        Lawyer(
            id = 5,
            name = "Luật sư Hoàng Minh Đức",
            specialties = listOf("Thuế", "Kế toán", "Tài chính"),
            industries = listOf("Tài chính", "Kế toán", "Ngân hàng"),
            city = "Hà Nội",
            rating = 4.6f,
            reviews = 78,
            experience = "10 năm",
            pricePerHour = "550.000đ/giờ",
            avatarUrl = "",
            responseTime = "30 phút",
            description = "Chuyên gia về luật thuế và kế toán, giúp doanh nghiệp tối ưu hóa nghĩa vụ thuế một cách hợp pháp. Có kinh nghiệm xử lý các vụ việc tranh chấp thuế phức tạp.",
            phoneNumber = "0903456789",
            isOnline = true,
            address = "Tầng 5, Tòa nhà Keangnam Landmark, Quận Nam Từ Liêm, Hà Nội",
            education = "Thạc sĩ Luật Thuế - Học viện Tài chính",
            languages = listOf("Tiếng Việt", "English"),
            certifications = listOf("Chứng chỉ hành nghề luật sư", "CPA", "Chứng chỉ tư vấn thuế"),
            successRate = 91.3f,
            totalCases = 167
        ),
        Lawyer(
            id = 6,
            name = "Luật sư Vũ Thị Mai",
            specialties = listOf("Gia đình", "Hôn nhân", "Trẻ em"),
            industries = listOf("Cá nhân", "Gia đình", "Xã hội"),
            city = "Cần Thơ",
            rating = 4.8f,
            reviews = 92,
            experience = "7 năm",
            pricePerHour = "400.000đ/giờ",
            avatarUrl = "",
            responseTime = "12 phút",
            description = "Chuyên về luật gia đình và hôn nhân, giúp giải quyết các tranh chấp gia đình một cách hòa bình và hiệu quả. Có kinh nghiệm trong việc bảo vệ quyền lợi trẻ em và phụ nữ.",
            phoneNumber = "0908765432",
            isOnline = true,
            address = "Tầng 3, Tòa nhà Saigon Trade Center, Quận Ninh Kiều, Cần Thơ",
            education = "Cử nhân Luật - Đại học Cần Thơ",
            languages = listOf("Tiếng Việt"),
            certifications = listOf("Chứng chỉ hành nghề luật sư", "Chứng chỉ tư vấn tâm lý"),
            successRate = 94.1f,
            totalCases = 156
        ),
        Lawyer(
            id = 7,
            name = "Luật sư Đặng Quốc Bảo",
            specialties = listOf("Đầu tư", "M&A", "Doanh nghiệp"),
            industries = listOf("Đầu tư", "Tài chính", "Doanh nghiệp"),
            city = "Hồ Chí Minh",
            rating = 4.9f,
            reviews = 145,
            experience = "11 năm",
            pricePerHour = "900.000đ/giờ",
            avatarUrl = "",
            responseTime = "8 phút",
            description = "Chuyên gia về luật đầu tư và M&A, tư vấn cho các giao dịch lớn. Có kinh nghiệm trong việc cấu trúc các thương vụ phức tạp và đàm phán hợp đồng quốc tế.",
            phoneNumber = "0901122334",
            isOnline = true,
            address = "Tầng 25, Tòa nhà Vietcombank Tower, Quận 1, TP.HCM",
            education = "Thạc sĩ Luật Kinh doanh Quốc tế - Đại học Luật TP.HCM",
            languages = listOf("Tiếng Việt", "English", "中文"),
            certifications = listOf("Chứng chỉ hành nghề luật sư", "Chứng chỉ M&A Specialist"),
            successRate = 97.2f,
            totalCases = 89
        ),
        Lawyer(
            id = 8,
            name = "Luật sư Ngô Thị Lan",
            specialties = listOf("Môi trường", "Xây dựng", "Quy hoạch"),
            industries = listOf("Xây dựng", "Môi trường", "Năng lượng"),
            city = "Hà Nội",
            rating = 4.5f,
            reviews = 67,
            experience = "9 năm",
            pricePerHour = "480.000đ/giờ",
            avatarUrl = "",
            responseTime = "25 phút",
            description = "Chuyên gia về luật môi trường và xây dựng. Tư vấn cho các dự án lớn về tuân thủ quy định môi trường và thủ tục xây dựng. Có kinh nghiệm xử lý các vụ việc tranh chấp đất đai.",
            phoneNumber = "0904567890",
            isOnline = false,
            address = "Tầng 7, Tòa nhà Hà Nội Tower, Quận Hai Bà Trưng, Hà Nội",
            education = "Thạc sĩ Luật Môi trường - Đại học Tài nguyên và Môi trường Hà Nội",
            languages = listOf("Tiếng Việt", "English"),
            certifications = listOf("Chứng chỉ hành nghề luật sư", "Chứng chỉ đánh giá tác động môi trường"),
            successRate = 89.4f,
            totalCases = 123
        )
    )

    suspend fun getAllLawyers(): Flow<Result<List<Lawyer>>> = flow {
        try {
            emit(Result.success(emptyList())) // Loading state
            delay(1000) // Simulate network delay
            emit(Result.success(mockLawyers))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun searchLawyers(
        query: String = "",
        city: String = "Tất cả",
        field: String = "Tất cả",
        industry: String = "Tất cả",
        minRating: Float = 0f,
        onlineOnly: Boolean = false
    ): Flow<Result<List<Lawyer>>> = flow {
        try {
            emit(Result.success(emptyList())) // Loading state
            delay(800) // Simulate network delay
            
            val filteredLawyers = mockLawyers.filter { lawyer ->
                val matchesSearch = query.isEmpty() ||
                        lawyer.name.contains(query, ignoreCase = true) ||
                        lawyer.specialties.any { it.contains(query, ignoreCase = true) } ||
                        lawyer.industries.any { it.contains(query, ignoreCase = true) } ||
                        lawyer.description.contains(query, ignoreCase = true)

                val matchesCity = city == "Tất cả" || lawyer.city == city
                val matchesField = field == "Tất cả" || lawyer.specialties.contains(field)
                val matchesIndustry = industry == "Tất cả" || lawyer.industries.contains(industry)
                val matchesRating = lawyer.rating >= minRating
                val matchesOnlineStatus = !onlineOnly || lawyer.isOnline

                matchesSearch && matchesCity && matchesField && matchesIndustry && matchesRating && matchesOnlineStatus
            }
            
            emit(Result.success(filteredLawyers))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getLawyerById(id: Int): Lawyer? {
        return mockLawyers.find { it.id == id }
    }

    // Static data for filters
    fun getCities(): List<String> = listOf("Tất cả", "Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "Cần Thơ", "Hải Phòng", "Nha Trang", "Huế")
    
    fun getLegalFields(): List<String> = listOf(
        "Tất cả", "Dân sự", "Hình sự", "Lao động", "Hợp đồng", 
        "Sở hữu trí tuệ", "Tố tụng", "Bảo hiểm", "Thuế", "Gia đình", 
        "Hôn nhân", "Bất động sản", "Doanh nghiệp", "Đầu tư", "M&A", 
        "Môi trường", "Xây dựng", "An ninh mạng"
    )
    
    fun getIndustries(): List<String> = listOf(
        "Tất cả", "Bất động sản", "Công nghệ", "Tài chính", "Sản xuất", 
        "Xây dựng", "Ngân hàng", "Khởi nghiệp", "Kế toán", "Cá nhân", 
        "Gia đình", "Dịch vụ", "E-commerce", "Đầu tư", "Môi trường", 
        "Năng lượng", "Bảo mật", "Xã hội"
    )
}
