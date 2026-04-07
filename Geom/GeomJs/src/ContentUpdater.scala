/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, pWeb.*

/** Updates HTML content due to changes from HTML input or Select elements. */
sealed trait ContentUpdater

object ContentUpdater
{
  def apply(inputer: InputUpdater): ContentUpdater = inputer match
  { case iun: InputUpdaterNum => ContentUpdaterNum(iun)
    case iut: InputUpdaterText => ContentUpdaterText(iut)
  }
}

/** Updates HTML content due to number changes from HTML input elements. */
class ContentUpdaterNum(val inputer: InputUpdaterNum) extends ContentUpdater
{ val idStem = inputer.idStr
  val inpElem = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", listner)

  def listner: Event => Unit = e =>
  { val newInpStr = e.target.asInstanceOf[html.Input].value
    val newNum = newInpStr.toDouble
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.depends.foreach{ (dep: Callback1Num) =>
      val targetId = dep.targetId
      val target = document.getElementById(targetId)
      if (target == null) deb(s" target is null from inputer $inputer for id: $targetId.")
      else target.textContent = dep.f(newNum)
    }
  }
}

object ContentUpdaterNum
{
  def apply(inputer: InputUpdaterNum): ContentUpdaterNum = new ContentUpdaterNum(inputer)
}

/** Updates HTML content due to[[String]] changes from HTML input elements. */
class ContentUpdaterText(val inputer: InputUpdaterText) extends ContentUpdater
{
  val idStem = inputer.idStr
  val inpElem = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", listner(_))
  
  def listner: Event => Unit = e =>
  { val newInpStr = e.target.asInstanceOf[html.Input].value
    val len = inputer.clientCount
    deb(s"Updating $len textContents with value $newInpStr")
    inputer.depends.foreach { (dep: CallbackInput) =>
      val targetId = dep.targetId
      val target = document.getElementById(targetId)
      if (target == null) deb(s" target is null from inputer $inputer for id: $targetId.")
      else
      { target.textContent = dep match
        { case cb2: Callback2Text =>
          { val inp2Val: String = document.getElementById(cb2.otherInpIdStr).asInstanceOf[html.Input].value
            cb2 match
            { case Callback2Text1(targetId, inp2Id, f) => f(newInpStr, inp2Val)
              case Callback2Text2(targetId, inp2Id, f) => f(inp2Val, newInpStr)
            }
          }
          case Callback1Text(idStr, f) => f(newInpStr)
        }
      }
    }
  }  
}

object ContentUpdaterText
{
  def apply(inputer: InputUpdaterText): ContentUpdaterText = new ContentUpdaterText(inputer)
}