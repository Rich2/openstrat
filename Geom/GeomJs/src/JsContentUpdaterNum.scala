/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Updates HTML content due to number changes from HTML input [[Int]] elements. */
class JsContentUpdaterInt(val inputer: UpdaterIntInput) extends JsContentUpdater
{ val idStem: String = inputer.idStr
  val inpElem: html.Input = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", eventListener)

  def eventListener: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Input].value
    val newNum: Int = newInpStr.toInt
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.listeners.foreach{
      case Callback1IntText(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if(listener == null) deb(s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newNum)
      }
      case CallbackOptInt2Html(targetId, input1, f) =>
      { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        val inp1Option = input1.strToOption(inp1Val)
        val listener: Element = document.getElementById(targetId)
        listener.innerHTML = f(inp1Option, newNum).out
      }
    }   
  }
}

object JsContentUpdaterInt
{ /** Factory apply method for JavaScript to update HTML element listener list from updated number input. */
  def apply(inputer: UpdaterIntInput): JsContentUpdaterInt = new JsContentUpdaterInt(inputer)
}

/** Updates HTML content due to number changes from HTML input elements. */
class JsContentUpdaterDbl(val inputer: UpdaterDblInput) extends JsContentUpdater
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
      case Callback1DblHtml(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if (listener == null) deb(s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newNum).out
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
      case CallbackOptDbl2Text(targetId, input1, f) =>
      { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        val inp1Option = input1.strToOption(inp1Val)
        val target: Element = document.getElementById(targetId)
        target.textContent = f(inp1Option, newNum)
      }
    }
  }
}

object JsContentUpdaterDbl
{ /** Factory apply method for JavaScript to update HTML element listener list from updated number input. */
  def apply(inputer: UpdaterDblInput): JsContentUpdaterDbl = new JsContentUpdaterDbl(inputer)
}