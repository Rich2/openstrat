/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, pWeb.*

/** Finds a text input in an HTML page and adds event listener to change values in the page. */
case class HtmlClassTextModder(idStr: String, className: String, initValue: String)
{ val inpElem: html.Input = document.getElementById(idStr).asInstanceOf[html.Input]
  var currValue = initValue
  inpElem.addEventListener("change", e => {
    val newUserName = e.target.asInstanceOf[html.Input].value
    val array = document.getElementsByClassName(className)
    deb(s"Found ${array.length} instances of $className")
    array.foreach { sp1 =>
      val sp2 = sp1.asInstanceOf[HTMLElement]
      val str = sp2.textContent
      val regex = currValue.r
      val newText = regex.replaceAllIn(str, newUserName)
      sp2.textContent = newText
    }
    currValue = newUserName
  })
}

class TextContentUpdater(val inputer: TextInput)
{
  val idStem = inputer.idStr
  val inpElem = document.getElementById(idStem).asInstanceOf[html.Input]
  inpElem.addEventListener("change", e => {
    val newInpStr = e.target.asInstanceOf[html.Input].value
    val len = inputer.dependsLen
    deb(s"Updating $len textContents with value $newInpStr")
    iUntilForeach(inputer.dependsLen){i =>
      val callBack: CallbackInput = inputer.depends(i)
      val target = document.getElementById(callBack.targetId)
      target.textContent = callBack match
      { case Callback1Text(idStr, f) => f(newInpStr)
        case cb2: Callback2Text =>
        { val inp2Val: String = document.getElementById(cb2.inp2Id).asInstanceOf[html.Input].value
          cb2 match
          { case Callback2Text1(targetId, inp2Id, f) => f(newInpStr, inp2Val)
            case Callback2Text2(targetId, inp2Id, f) => f(inp2Val, newInpStr)
          }
        }
      }
    }
  })
}

object TextContentUpdater
{
  def apply(inputer: TextInput): TextContentUpdater = new TextContentUpdater(inputer)
}