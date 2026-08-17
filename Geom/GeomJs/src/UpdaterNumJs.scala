/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Updates HTML content due to number changes from HTML input [[Int]] elements. */
class UpdaterIntJs(val inputer: UpdaterIntInput, val domElem: html.Element) extends JsUpdater
{ deb(s"Found $inputer with ${inputer.listenersListStr} listeners")
  domElem.addEventListener("change", eventListener)

  def eventListener: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Input].value
    val newNum: Int = newInpStr.toInt
    val len: Int = inputer.numListeners
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.callbacks.foreach{
      case CallbackIntText(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if(listener == null) deb(s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newNum)
      }
      case CallbackOptInt2Html(listenerId, input1, f) =>
      { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        val inp1Option: OptionHtml = input1.strToOption(inp1Val)
        val listener: Element = document.getElementById(listenerId)
        listener.innerHTML = f(inp1Option, newNum).out
      }
      case CallbackOptInt2Text(listenerId, input1, f) =>
      { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        val inp1Option: OptionHtml = input1.strToOption(inp1Val)
        val target: Element = document.getElementById(listenerId)
        target.textContent = f(inp1Option, newNum)
      }
    }   
  }
}

object UpdaterIntJs
{ /** Factory apply method for JavaScript to update HTML element listener list from updated number input. */
  def apply(inputer: UpdaterIntInput, domElem: html.Element): UpdaterIntJs = new UpdaterIntJs(inputer, domElem)
}

/** Updates HTML content due to number changes from HTML input elements. */
class JsUpdaterDbl(val inputer: UpdaterDblInput, val domElem: html.Element) extends JsUpdater
{ deb(s"Found $inputer with listener IDs: ${inputer.listenersListStr}.")
  domElem.addEventListener("change", eventListener)

  /** Not sure how this actually works. */
  def eventListener: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Input].value
    val newNum: Double = newInpStr.toDouble
    val len: Int = inputer.numListeners
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.callbacks.foreach{
      case CallbackDblText(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if (listener == null) deb(s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newNum)
      }
      case CallbackDblHtml(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if (listener == null) deb(s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newNum).out
      }
      case CallbackStrDbl2(listenerId, input1, f) =>
      { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        f(inp1Val, newNum)
      }
      case CallbackOptDbl2Html(listenerId, input1, f) =>
      { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        val inp1Option: OptionHtml = input1.strToOption(inp1Val)
        val target: Element = document.getElementById(listenerId)
        target.innerHTML = f(inp1Option, newNum).out
      }
      case CallbackOptDbl2Text(listenerId, input1, f) =>
      { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        val inp1Option: OptionHtml = input1.strToOption(inp1Val)
        val target: Element = document.getElementById(listenerId)
        target.textContent = f(inp1Option, newNum)
      }
    }
  }
}

object JsUpdaterDbl
{ /** Factory apply method for JavaScript to update HTML element listener list from updated number input. */
  def apply(inputer: UpdaterDblInput, domElem: html.Element): JsUpdaterDbl = new JsUpdaterDbl(inputer, domElem)
}