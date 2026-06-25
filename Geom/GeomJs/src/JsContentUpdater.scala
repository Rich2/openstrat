/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Base trait for JavaScript to updates HTML content due to changes from HTML input or Select elements. */
trait JsContentUpdater

object JsContentUpdater
{ /** Factory apply method, constructs the appropriate [[JsContentUpdater]] for the given [[UpdaterInputLike]]. */
  def apply(inputer: UpdaterInputLike): JsContentUpdater = inputer match
  { case iun: UpdaterDblInput => JsContentUpdaterNum(iun)
    case iut: UpdaterStr => JsUpdaterStr(iut)
    case iua: UpdaterOption => UpdaterOptionJs(iua)
  }
}

/** Updates HTML content due to number changes from HTML input elements. */
class JsContentUpdaterNum(val inputer: UpdaterDblInput) extends JsContentUpdater
{ val idStem: String = inputer.idStr
  val inpElem: html.Input = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", eventListener)

  def eventListener: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Input].value
    val newNum: Double = newInpStr.toDouble
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.listeners.foreach{
      case Callback1DblText(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if (listener == null) deb(s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newNum)
      }
      case CallbackStrDbl2(listenerId, input1IdStr, f) =>
      { val inp1Val: String = document.getElementById(input1IdStr).asInstanceOf[html.Input].value
        f(inp1Val, newNum)
      }
      case CallbackOptDbl2Html(targetId, input1, f) =>
      { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        val inp1Option = input1.strToOption(inp1Val)
        val target: Element = document.getElementById(targetId)
        target.innerHTML = f(inp1Option, newNum).out
      }
    }
  }
}

object JsContentUpdaterNum
{ /** Factory apply method for JavaScript to update HTML element listener list from updated number input. */
  def apply(inputer: UpdaterDblInput): JsContentUpdaterNum = new JsContentUpdaterNum(inputer)
}

/** Updates HTML content due to number changes from HTML input elements. */
class UpdaterOptionJs(val inputer: UpdaterOption) extends JsContentUpdater
{ val idStem: String = inputer.idStr
  val inpElem: html.Select = document.getElementById(idStem).asInstanceOf[html.Select]
  inpElem.addEventListener("change", eventListener)

  def eventListener: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Select].value
    val newOption: OptionHtml = inputer.contents.find(_.valueStr == newInpStr).getOrElse(OptionNotFound)
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.callBacks.foreach{
      case Callback1OptHtml(listenerId, f) =>
      { val target = document.getElementById (listenerId)
        if (target == null) deb (s" target is null from inputer $inputer for id: $listenerId.")
        else target.innerHTML = f(newOption).out
      }
      case CallbackOptionNum1(listenerId, input2IdStr, f) => {
        val listener: Element = document.getElementById(listenerId)
        val inp2Val: Double = document.getElementById(input2IdStr).asInstanceOf[html.Input].value.toDouble
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption, inp2Val).out
      }
    }
  }
}

extension (page: PageHtmlUpdater)
{ /** Constructs a JavaScript [[JsContentUpdater]] for each [[PageHtmlUpdater]]. */
  def jsAgg: Unit =
  { val num = page.inpAcc.length
    deb(s"Found $num in ${page.fileName.str}")
    page.inpAcc.foreach(inputUpdater => JsContentUpdater(inputUpdater))
}
}