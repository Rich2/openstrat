/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Base trait for JavaScript to updates HTML content due to changes from HTML input or Select elements. */
sealed trait JsContentUpdater

object JsContentUpdater
{ /** Factory apply method, constructs the appropriate [[JsContentUpdater]] for the given [[UpdaterInputLike]]. */
  def apply(inputer: UpdaterInputLike): JsContentUpdater = inputer match
  { case iun: UpdaterNumInput => JsContentUpdaterNum(iun)
    case iut: UpdaterText => JsContentUpdaterText(iut)
    case iua: UpdaterSelectAny => JsContentUpdaterSelect(iua)
  }
}

/** Updates HTML content due to number changes from HTML input elements. */
class JsContentUpdaterNum(val inputer: UpdaterNumInput) extends JsContentUpdater
{
  val idStem: String = inputer.idStr
  val inpElem: html.Input = document.getElementById(idStem).asInstanceOf[html.Input]
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

object JsContentUpdaterNum
{ /** Factory apply method for JavaScript to update HTML element listener list from updated number input. */
  def apply(inputer: UpdaterNumInput): JsContentUpdaterNum = new JsContentUpdaterNum(inputer)
}

/** JavaScript updates HTML content due to [[String]] changes from HTML input elements. */
class JsContentUpdaterText(val inputer: UpdaterText) extends JsContentUpdater
{ val idStem: String = inputer.idStr
  val inpElem: html.Input = document.getElementById(idStem).asInstanceOf[html.Input]
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

object JsContentUpdaterText
{ /** Factory apply method for JavaScript to update HTML element listener list from updated text input. */
  def apply(inputer: UpdaterText): JsContentUpdaterText = new JsContentUpdaterText(inputer)
}

/** Updates HTML content due to number changes from HTML input elements. */
class JsContentUpdaterSelect(val inputer: UpdaterSelectAny) extends JsContentUpdater
{
  val idStem: String = inputer.idStr
  val inpElem: html.Input = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", listner)

  def listner: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Select].value
    val newAny: Any = inputer.contents.find(_.valueStr == newInpStr).getOrElse(None)
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.callBacks.foreach{ cb =>
      val listenerId = cb.targetId
      val target = document.getElementById(listenerId)
      if (target == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
      else target.innerHTML = cb.f(newAny).out
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