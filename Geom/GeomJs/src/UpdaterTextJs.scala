/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** JavaScript updates HTML content due to [[String]] changes from HTML input elements. */
class UpdaterTextJs(val inputer: UpdaterText) extends UpdaterJs
{ val idStem: String = inputer.idStr
  val inpElem: html.Input = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", eventListener(_))

  def eventListener: Event => Unit = e =>
  { val newInpStr = e.target.asInstanceOf[html.Input].value
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.callBacks.foreach { (dep: CallbackInput) =>
      val targetId: String = dep.listenerId
      val target: Element = document.getElementById(targetId)
      if (target == null) deb(s" target is null from inputer $inputer for id: $targetId.")
      else
      { 
        target.innerHTML = dep match
        { case Callback1Text(idStr, f) => f(newInpStr)
          case CallBack1StrHtml(idStr, f) => f(newInpStr).out
          case Callback2Text1(targetId, input2IdStr, f) =>
          { val inp2Val: String = document.getElementById(input2IdStr).asInstanceOf[html.Input].value
            f(newInpStr, inp2Val)
          }
          case Callback2Text2(targetId, input1IdStr, f) =>
          { val inp1Val: String = document.getElementById(input1IdStr).asInstanceOf[html.Input].value
            f(inp1Val, newInpStr)
          }
          case Callback3Text1(targetId, input2IdStr, input3IdStr, f) =>
          { val inp2Val: String = document.getElementById(input2IdStr).asInstanceOf[html.Input].value
            val inp3Val: String = document.getElementById(input3IdStr).asInstanceOf[html.Input].value
            f(newInpStr, inp2Val, inp3Val)
          }
          case Callback3Text2(targetId, input1IdStr, input3IdStr, f) =>
          { val inp1Val: String = document.getElementById(input1IdStr).asInstanceOf[html.Input].value
            val inp3Val: String = document.getElementById(input3IdStr).asInstanceOf[html.Input].value
            f(inp1Val, newInpStr, inp3Val)
          }
          case Callback3Text3(targetId, input1IdStr, input2IdStr, f) =>
          { val inp1Val: String = document.getElementById(input1IdStr).asInstanceOf[html.Input].value
            val inp2Val: String = document.getElementById(input2IdStr).asInstanceOf[html.Input].value
            f(inp1Val, inp2Val, newInpStr)
          }
          case CallbackTextNum1(targetId, input2IdStr, f) =>
          { val inp2Val: Double = document.getElementById(input2IdStr).asInstanceOf[html.Input].value.toDouble
            f(newInpStr, inp2Val)
          }
        }
      }
    }
  }
}

object UpdaterTextJs
{ /** Factory apply method for JavaScript to update HTML element listener list from updated text input. */
  def apply(inputer: UpdaterText): UpdaterTextJs = new UpdaterTextJs(inputer)
}