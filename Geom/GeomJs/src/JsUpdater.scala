/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Base trait for JavaScript to updates HTML content due to changes from HTML input or Select elements. */
trait JsUpdater

object JsUpdater
{ /** Factory apply method, constructs the appropriate [[JsUpdater]] for the given [[UpdaterInputLike]]. */
  def apply(inputer: UpdaterInputLike): JsUpdater = inputer match
  { case uii: UpdaterIntInput => JsUpdaterInt(uii)
    case udi: UpdaterDblInput => JsUpdaterDbl(udi)
    case iut: UpdaterStr => JsUpdaterStr(iut)
    case iua: UpdaterOption => UpdaterOptionJs(iua)
  }
}

/** Updates HTML content due to number changes from HTML input elements. */
class UpdaterOptionJs(val inputer: UpdaterOption) extends JsUpdater
{ val idStem: String = inputer.idStr
  val inpElem: html.Select = document.getElementById(idStem).asInstanceOf[html.Select]
  inpElem.addEventListener("change", eventListener)

  def eventListener: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Select].value
    val newOption: OptionHtml = inputer.contents.find(_.valueStr == newInpStr).getOrElse(OptionNotFound)
    val len = inputer.clientCount
    deb(s"OpdaterOptionJs updating $len textContent / innerHTML with value $newInpStr")
    inputer.callBacks.foreach{
      case Callback1OptHtml(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if (listener == null) deb (s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption).out
      }
      case Callback1OptText(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if (listener == null) deb (s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newOption)
      }
      case CallbackOptInt1Html(listenerId, input2IdStr, f) =>
      { val listener: Element = document.getElementById(listenerId)
        val inp2Val: Int = document.getElementById(input2IdStr).asInstanceOf[html.Input].value.toInt
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption, inp2Val).out
      }
      case CallbackOptInt1Text(listenerId, input2IdStr, f) =>
      { val listener: Element = document.getElementById(listenerId)
        val inp2Val: Int = document.getElementById(input2IdStr).asInstanceOf[html.Input].value.toInt
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newOption, inp2Val)
      }
      case CallbackOptDbl1Html(listenerId, input2IdStr, f) => {
        val listener: Element = document.getElementById(listenerId)
        val inp2Val: Double = document.getElementById(input2IdStr).asInstanceOf[html.Input].value.toDouble
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption, inp2Val).out
      }
      case CallbackOptDbl1Text(listenerId, input2IdStr, f) => {
        val listener: Element = document.getElementById(listenerId)
        val inp2Val: Double = document.getElementById(input2IdStr).asInstanceOf[html.Input].value.toDouble
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newOption, inp2Val)
      } 
    }
  }
}

extension (page: PageHtmlUpdater)
{ /** Constructs a JavaScript [[JsUpdater]] for each [[PageHtmlUpdater]]. */
  def jsAgg: Unit =
  { val num = page.inpAcc.length
    deb(s"Found $num in ${page.fileName.str}")
    page.inpAcc.foreach(inputUpdater => JsUpdater(inputUpdater))
}
}