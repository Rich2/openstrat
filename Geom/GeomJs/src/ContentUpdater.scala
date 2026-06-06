/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*
import org.scalajs.dom.html.Input
import pweb.*

/** Base trait for JavaScript to updates HTML content due to changes from HTML input or Select elements. */
sealed trait ContentUpdater

object ContentUpdater
{ /** Factory apply method, constructs the appropriate [[ContentUpdater]] for the given [[InputLikeUpdater]] */
  def apply(inputer: InputLikeUpdater): ContentUpdater = inputer match
  { case iun: UpdaterNumInput => ContentUpdaterNum(iun)
    case iut: UpdaterText => ContentUpdaterText(iut)
  }
}

/** Updates HTML content due to number changes from HTML input elements. */
class ContentUpdaterNum(val inputer: UpdaterNumInput) extends ContentUpdater
{ val idStem = inputer.idStr
  val inpElem = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", listner)

  def listner: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Input].value
    val newNum: Double = newInpStr.toDouble
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.depends.foreach{
      case dep: Callback1Num =>
      { val listenerId = dep.targetId
        val target = document.getElementById(listenerId)
        if (target == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else target.textContent = dep.f(newNum)
      }
      case CallbackTextNum2(targetId, input1IdStr, f) =>
      { val inp1Val: String = document.getElementById(input1IdStr).asInstanceOf[html.Input].value
        f(inp1Val, newNum)
      }
    }
  }
}

object ContentUpdaterNum
{ /** Factory apply method for JavaScript to update HTML element listener list from updated number input. */
  def apply(inputer: UpdaterNumInput): ContentUpdaterNum = new ContentUpdaterNum(inputer)
}

/** JavaScript updates HTML content due to [[String]] changes from HTML input elements. */
class ContentUpdaterText(val inputer: UpdaterText) extends ContentUpdater
{ val idStem: String = inputer.idStr
  val inpElem: Input = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", listner(_))
  
  def listner: Event => Unit = e =>
  { val newInpStr = e.target.asInstanceOf[html.Input].value
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.callBacks.foreach { (dep: CallbackInput) =>
      val targetId: String = dep.targetId
      val target: Element = document.getElementById(targetId)
      if (target == null) deb(s" target is null from inputer $inputer for id: $targetId.")
      else
      { target.innerHTML = dep match
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
        }
      }
    }
  }  
}

object ContentUpdaterText
{ /** Factory apply method for JavaScript to update HTML element listener list from updated text input. */
  def apply(inputer: UpdaterText): ContentUpdaterText = new ContentUpdaterText(inputer)
}