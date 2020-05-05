package cz.bald.student_tests.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import cz.bald.studenttests.R
import kotlinx.android.synthetic.main.fragment_test_question_open.view.*
import kotlinx.android.synthetic.main.fragment_test_question_test.view.*
import kotlinx.android.synthetic.main.fragment_test_section.view.*
import java.lang.IllegalArgumentException

class QuestionFragment(private val test: List<Int>, private val index: Int,
                       private val sectionIndex: Int, private val navIndex: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true

        val view = when(test[index]) {
            1 -> createSectionView(inflater, container)
            2 -> createTestQuestionView(inflater, container)
            3 -> createOpenQuestionView(inflater, container)
            else -> throw IllegalArgumentException()
        }

        return view
    }

    private fun createSectionView(inflater: LayoutInflater, container: ViewGroup?): View {
        val frag = inflater.inflate(R.layout.fragment_test_section, container, false)

        frag.section_text.text = "Po vyjdení Maríny tlačou v auguste 1846 sa študujúca mládež veršami o láske, kráse " +
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
                "prajeme s túžbou nádejnou ďalšie jeho práce očakávajúc.“"

        if (index > 0) {
            frag.section_back_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, index - 1, sectionIndex, Int.MIN_VALUE))
            }
        } else {
            frag.section_back_button.visibility = View.INVISIBLE
        }
        if (navIndex >= 0) {
            frag.section_question_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, navIndex, index, Int.MIN_VALUE))
            }
        } else {
            frag.section_question_button.visibility = View.INVISIBLE
        }
        if (index < test.size - 1) {
            frag.section_next_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, index + 1, index, Int.MIN_VALUE))
            }
        } else {
            frag.section_next_button.visibility = View.INVISIBLE
        }

        return frag
    }

    private fun createTestQuestionView(inflater: LayoutInflater, container: ViewGroup?): View {
        val frag = inflater.inflate(R.layout.fragment_test_question_test, container, false)

        frag.question_test_question_number_value.text = (index + 1).toString() + " / " + test.size
        frag.question_test_text.text = "Autor ukážky sa v nej zameriava"
        frag.question_test_answers.adapter = ArrayAdapter<String>(this.requireContext(),
            android.R.layout.simple_list_item_1, arrayOf("(A) na vlastné hodnotenie prínosu Maríny pre slovenskú literatúru.",
                "(B) na štylistickú analýzu jazykových kvalít básnického diela Marína.",
                "(C) na zhrnutie názorov Sládkovičových súčasníkov na dielo Marína.",
                "(D) na kritiku subjektívneho pohľadu literárnych recenzentov Maríny."))

        frag.question_test_answers.setOnItemClickListener { adapterView, view, i, l ->
            view.isSelected = true
        }
        if (index > 0) {
            frag.question_test_back_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, index - 1, sectionIndex, Int.MIN_VALUE))
            }
        } else {
            frag.section_back_button.visibility = View.INVISIBLE
        }
        if (sectionIndex >= 0) {
            frag.question_test_section_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, sectionIndex, sectionIndex, index))
            }
        } else {
            frag.section_question_button.visibility = View.INVISIBLE
        }
        if (index < test.size - 1) {
            frag.question_test_next_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, index + 1, sectionIndex, Int.MIN_VALUE))
            }
        } else {
            frag.section_next_button.visibility = View.INVISIBLE
        }

        return frag
    }

    private fun createOpenQuestionView(inflater: LayoutInflater, container: ViewGroup?): View {
        val frag = inflater.inflate(R.layout.fragment_test_question_open, container, false)

        frag.question_open_question_number_value.text = (index + 1).toString() + " / " + test.size
        frag.question_open_text.text = "Napíšte názov diela Jána Kollára, na ktoré sa odvolával J. Lomenčík v úvode ukážky " +
                "a ktorého názov patrí na zakryté miesto v nej?"

        if (index > 0) {
            frag.question_open_back_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, index - 1, sectionIndex, Int.MIN_VALUE))
            }
        } else {
            frag.section_back_button.visibility = View.INVISIBLE
        }
        if (sectionIndex >= 0) {
            frag.question_open_section_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, sectionIndex, sectionIndex, index))
            }
        } else {
            frag.section_question_button.visibility = View.INVISIBLE
        }
        if (index < test.size - 1) {
            frag.question_open_next_button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, index + 1, sectionIndex, Int.MIN_VALUE))
            }
        } else {
            frag.section_next_button.visibility = View.INVISIBLE
        }

        return frag
    }
}