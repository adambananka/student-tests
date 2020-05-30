package cz.bald.student_tests.ui.test

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cz.bald.student_tests.enums.QuestionType
import cz.bald.student_tests.model.Question
import cz.bald.student_tests.model.Section
import cz.bald.student_tests.model.TestSetting
import cz.bald.student_tests.service.JsonConvertManager
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import cz.bald.student_tests.ui.setup.SetupActivity
import cz.bald.studenttests.R
import java.io.File


class TestActivity : AppCompatActivity(), FragmentChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        if (savedInstanceState == null) {
            val setting = intent.getParcelableExtra<TestSetting>(SetupActivity.ARG_SETUP)
            Toast.makeText(this, setting.toString(), Toast.LENGTH_LONG).show()

            val q1 = Question(1, 1, QuestionType.TEST, "Autor ukážky sa v nej zameriava",
                listOf("(A) na vlastné hodnotenie prínosu Maríny pre slovenskú literatúru.",
                    "(B) na štylistickú analýzu jazykových kvalít básnického diela Marína.",
                    "(C) na zhrnutie názorov Sládkovičových súčasníkov na dielo Marína.",
                    "(D) na kritiku subjektívneho pohľadu literárnych recenzentov Maríny."),
                "1", "")
            val q2 = q1.copy(number = 2)
            val q3 = Question(3, 1, QuestionType.OPEN,
                "Napíšte názov diela Jána Kollára, na ktoré sa odvolával J. Lomenčík v úvode ukážky a ktorého názov patrí na zakryté miesto v nej?",
                emptyList(), "to najlepsie", "")
            val q4 = q1.copy(number = 4)
            val s1 = Section(1,
                "Po vyjdení Maríny tlačou v auguste 1846 sa študujúca mládež veršami o láske, kráse " +
                        "a mladosti nadchýnala. V myšlienkovej i citovej oblasti jej vlastne nahradila Kollárovu " +
                        ". Teraz už nemusela srdce rozpolťovať, pretože národnobuditeľské poslanie " +
                        "mohla prejavovať aj cez vnútorné poryvy duše. Potvrdzuje to i odporúčanie z pera dobového " +
                        "kritika Mikuláša Dohnányho v Orle tatranskom 1847: „Tu sa vám svety otvoria, po ktorých ste " +
                        "dávno túžili, a nájdete sa v nich ako vo svojich vlastných.“ Vo vynikajúcej recenzii vyzdvihol " +
                        "Sládkovičovo „vystúpenie z kože“ biblickej češtiny, čím položil slovenským slovom pevné " +
                        "základy slovenskej poézie: „Poďte sa prizrieť kráse reči našej, vy posmievači, keď ona v kráse " +
                        "spieva a k chórom anjelov božích sa približuje, a srdce vaše musí k nej zahorieť láskou. Ukázal " +
                        "on, k akej dokonalosti slovenčina naša prísť môže!“ Z prvého kriticky nastaveného zrkadla " +
                        "pre obrazotvornosťou hýriacu poéziu Maríny jasne vidieť, že Dohnány si zobral na „mušku“ " +
                        "hlavne estetické kvality. Oceňujúc túto stránku vidí v tomto peknom kvete základný „ ... výtvor " +
                        "najčistejšej poézie veku budúceho“.\n" +
                        "Do kritického zrkadlenia prispel vo „svojich“ Slovenských pohľadoch (1847) i Jozef " +
                        "Miloslav Hurban, ktorý báseň nečítal šablónovito, zdôrazňujúc hlavne slobodu tvorenia naozaj " +
                        "originálneho výrazu. Záverom recenzie optimisticky Sládkoviča vyprevádza na cestu literárnym " +
                        "životom slovami: „My mu cestu jeho, tak pekne nastúpivšiemu všetku slávu a všetok prospech " +
                        "prajeme s túžbou nádejnou ďalšie jeho práce očakávajúc.“",
                4, 4, listOf(q1, q2, q3, q4))
            val q5 = q1.copy(number = 5)
            val q6 = q3.copy(number = 6)
            val q7 = q1.copy(number = 7)
            val s2 = s1.copy(number = 2, questionCount = 3, maxPoints = 3,
                questions = listOf(q5, q6, q7))
            //val test = Test(setting, listOf(s1, s2), 7, 7, 100, null)
            val test = JsonConvertManager.convertJsonToTest(File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ), "MATURITA_SJL_2019.json").readText())

            val fragment = QuestionFragment(test, 0, QuestionFragment.NO_QUESTION,
                QuestionFragment.NO_QUESTION)
            swapFragment(fragment, true)
        }
    }

    override fun swapFragment(newFragment: Fragment, stack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.test_fragment_container, newFragment)
        if (stack) {
            transaction.addToBackStack(newFragment.toString())
        }
        transaction.commit()
    }
}