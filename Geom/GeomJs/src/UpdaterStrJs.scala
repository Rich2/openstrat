/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** JavaScript updates HTML content due to [[String]] changes from HTML input elements. */
class UpdaterStrJs(val inputer: UpdaterStr, val domElem: html.Element) extends JsUpdater
{ deb(inputer.listenersSummary)
  domElem.addEventListener("change", eventListener)

  def eventListener: Event => Unit = e =>
  { val newInpStr = e.target.asInstanceOf[html.Input].value
    val len = inputer.numListeners
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.callBacks.foreach { callback =>
      val listenerId: String = callback.listenerId
      val listener: Element = document.getElementById(listenerId)
      if (listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
      else
      { 
        listener.innerHTML = callback match
        { case CallbackStrText(idStr, f) => f(newInpStr)
          case CallBackStrHtml(idStr, f) => f(newInpStr).out
          case Callback2Str1Text(targetId, input2, f) =>
          { val inp2Val: String = document.getElementById(input2.idStr).asInstanceOf[html.Input].value
            f(newInpStr, inp2Val)
          }
          case Callback2Str2Text(targetId, input1, f) =>
          { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
            f(inp1Val, newInpStr)
          }
          case Callback3Str1Text(targetId, input2, input3, f) =>
          { val inp2Val: String = document.getElementById(input2.idStr).asInstanceOf[html.Input].value
            val inp3Val: String = document.getElementById(input3.idStr).asInstanceOf[html.Input].value
            f(newInpStr, inp2Val, inp3Val)
          }
          case Callback3Str2Text(targetId, input1, input3, f) =>
          { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
            val inp3Val: String = document.getElementById(input3.idStr).asInstanceOf[html.Input].value
            f(inp1Val, newInpStr, inp3Val)
          }
          case Callback3Str3Text(targetId, input1, input2, f) =>
          { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
            val inp2Val: String = document.getElementById(input2.idStr).asInstanceOf[html.Input].value
            f(inp1Val, inp2Val, newInpStr)
          }
          case CallbackOpt2Str2Html(targetId, input1, input3, f) =>
          { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
            val inp1Option: OptionHtml = input1.strToOption(inp1Val)
            val inp3Val: String = document.getElementById(input3.idStr).asInstanceOf[html.Input].value
            f(inp1Option, newInpStr, inp3Val).out
          }
          case CallbackOpt2Str3Html(targetId, input1, input2, f) =>
          { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
            val inp1Option: OptionHtml = input1.strToOption(inp1Val)
            val inp2Val: String = document.getElementById(input2.idStr).asInstanceOf[html.Input].value
            f(inp1Option, inp2Val, newInpStr).out
          }
          case Callback2Opt2Str3Html(targetId, input1, input2, input4, f) =>
          { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
            val inp1Option: OptionHtml = input1.strToOption(inp1Val)
            val inp2Val: String = document.getElementById(input2.idStr).asInstanceOf[html.Input].value
            val inp2Option: OptionHtml = input2.strToOption(inp2Val)
            val inp4Val: String = document.getElementById(input4.idStr).asInstanceOf[html.Input].value
            f(inp1Option, inp2Option, newInpStr, inp4Val).out
          }
          case Callback2Opt2Str4Html(targetId, input1, input2, input3, f) =>
          { val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
            val inp1Option: OptionHtml = input1.strToOption(inp1Val)
            val inp2Val: String = document.getElementById(input2.idStr).asInstanceOf[html.Input].value
            val inp2Option: OptionHtml = input2.strToOption(inp2Val)
            val inp3Val: String = document.getElementById(input3.idStr).asInstanceOf[html.Input].value
            f(inp1Option, inp2Option, inp3Val, newInpStr).out
          }
          case CallbackStrDbl1Text(targetId, input2, f) =>
          { val inp2Val: Double = document.getElementById(input2.idStr).asInstanceOf[html.Input].value.toDouble
            f(newInpStr, inp2Val)
          }
        }
      }
    }
  }
}

object UpdaterStrJs
{ /** Factory apply method for JavaScript to update HTML element listener list from updated text input. */
  def apply(inputer: UpdaterStr, domElem: html.Element): UpdaterStrJs = new UpdaterStrJs(inputer, domElem)
}