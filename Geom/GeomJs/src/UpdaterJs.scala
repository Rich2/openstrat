/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Base trait for JavaScript to updates HTML content due to changes from HTML input or Select elements. */
trait UpdaterJs

object UpdaterJs
{ /** Factory apply method, constructs the appropriate [[UpdaterJs]] for the given [[UpdaterInputLike]]. */
  def apply(inputer: UpdaterInputLike): UpdaterJs = inputer match
  { case iun: UpdaterNumInput => JsContentUpdaterNum(iun)
    case iut: UpdaterText => UpdaterTextJs(iut)
    case iua: UpdaterSelectAny => JsContentUpdaterSelect(iua)
  }
}

/** Updates HTML content due to number changes from HTML input elements. */
class JsContentUpdaterNum(val inputer: UpdaterNumInput) extends UpdaterJs
{ val idStem: String = inputer.idStr
  val inpElem: html.Input = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", listner)

  def listner: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Input].value
    val newNum: Double = newInpStr.toDouble
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.listeners.foreach{
      case Callback1Num(listenerId, f) =>
      { val target = document.getElementById(listenerId)
        if (target == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else target.textContent = f(newNum)
      }
      case CallbackTextNum2(targetId, input1IdStr, f) =>
      { val inp1Val: String = document.getElementById(input1IdStr).asInstanceOf[html.Input].value
        f(inp1Val, newNum)
      }
      case CallbackOptionNum2(targetId, input1IdStr, f) => {
        val inp1Val: String = document.getElementById(input1IdStr).asInstanceOf[html.Input].value
        f(inp1Val, newNum)
      }
    }
  }
}

object JsContentUpdaterNum
{ /** Factory apply method for JavaScript to update HTML element listener list from updated number input. */
  def apply(inputer: UpdaterNumInput): JsContentUpdaterNum = new JsContentUpdaterNum(inputer)
}

/** Updates HTML content due to number changes from HTML input elements. */
class JsContentUpdaterSelect(val inputer: UpdaterSelectAny) extends UpdaterJs
{
  val idStem: String = inputer.idStr
  val inpElem: html.Select = document.getElementById(idStem).asInstanceOf[html.Select]
  inpElem.addEventListener("change", listner)

  def listner: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Select].value
    val newAny: Any = inputer.contents.find(_.valueStr == newInpStr).getOrElse(None)
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.callBacks.foreach{
      case Callback1Option(listenerId, f) =>
      { val target = document.getElementById (listenerId)
        if (target == null) deb (s" target is null from inputer $inputer for id: $listenerId.")
        else target.innerHTML = f(newAny).out
      }
      case CallbackOptionNum1(listenerId, input2IdStr, f) => {
        val listener: Element = document.getElementById(listenerId)
        val inp2Val: Double = document.getElementById(input2IdStr).asInstanceOf[html.Input].value.toDouble
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newAny, inp2Val).out
      }
    }
  }
}

extension (page: PageHtmlUpdater)
{ /** Constructs a JavaScript [[UpdaterJs]] for each [[PageHtmlUpdater]]. */
  def jsAgg: Unit =
  { val num = page.inpAcc.length
    deb(s"Found $num in ${page.fileName.str}")
    page.inpAcc.foreach(inputUpdater => UpdaterJs(inputUpdater))
}
}