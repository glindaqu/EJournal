package ru.glindaqu.ejournal.dataModels

/**
 * Simple excepting data format:
 *  student 1 {
 *      id: short
 *      name: string
 *      lastname: string
 *      patronymic: string
 *      info {
 *          date 1 {
 *              pair 1 {
 *                  uid: long
 *                  is absence: boolean
 *                  marks: short[]
 *              },
 *              pair 2 {
 *                  ...
 *              },
 *              ...
 *          },
 *          date 2 {
 *              ...
 *          },
 *          ...
 *      }
 *  }
 */
data class JournalRowData(
    val id: Int,
    val studentName: String,
    val studentLastname: String,
    val studentPatronymic: String,
    val data: List<StudentDay>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JournalRowData

        if (id != other.id) return false
        if (studentName != other.studentName) return false
        if (studentLastname != other.studentLastname) return false
        if (studentPatronymic != other.studentPatronymic) return false
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + studentName.hashCode()
        result = 31 * result + studentLastname.hashCode()
        result = 31 * result + studentPatronymic.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }
}

data class StudentDay(
    val date: Long,
    val pairs: Array<AcademicPair>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StudentDay

        return pairs.contentEquals(other.pairs)
    }

    override fun hashCode(): Int = pairs.contentHashCode()
}

data class AcademicPair(
    val uid: Long,
    val pairInfo: AcademicPairInfo,
    val isStudentAbsence: Boolean,
    val marks: Array<Short>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AcademicPair

        if (uid != other.uid) return false
        if (pairInfo != other.pairInfo) return false
        if (isStudentAbsence != other.isStudentAbsence) return false
        if (!marks.contentEquals(other.marks)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + isStudentAbsence.hashCode()
        result = 31 * result + marks.contentHashCode()
        return result
    }
}

data class AcademicPairInfo(
    val id: Int,
    val teacher: TeacherInfo,
    val title: String,
)

data class TeacherInfo(
    val id: Int,
    val name: String,
    val lastname: String,
    val patronymic: String,
)
