/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Updates HTML content due to number changes from HTML input elements. */
class UpdaterSelectJs(val inputer: UpdaterSelect, val inpElem: html.Element) extends JsUpdater
{ deb(s"Found $inputer with listener IDs: ${inputer.listenersListStr}.")
  inpElem.addEventListener("change", eventListener)

  def eventListener: Event => Unit = e =>
  { val newInpStr: String = e.target.asInstanceOf[html.Select].value
    val newOption: OptionHtml = inputer.contents.find(_.valueStr == newInpStr).getOrElse(OptionNotFound)
    val len = inputer.numListeners
    deb(s"OpdaterOptionJs updating $len textContent / innerHTML with value $newInpStr")
    inputer.callBacks.foreach{
      case CallbackOptHtml(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if (listener == null) deb (s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption).out
      }
      case CallbackOptText(listenerId, f) =>
      { val listener: Element = document.getElementById(listenerId)
        if (listener == null) deb (s" listener is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newOption)
      }
      case CallbackOpt2Str1Html(listenerId, input2, input3, f) =>
      { val listener: Element = document.getElementById(listenerId)
        val inp2Val: String = document.getElementById(input2.idStr).asInstanceOf[html.Input].value
        val inp3Val: String = document.getElementById(input3.idStr).asInstanceOf[html.Input].value
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption, inp2Val, inp3Val).out
      }
      case Callback2Opt2Str1Html(listenerId, input2, input3, input4, f) =>
      { val listener: Element = document.getElementById(listenerId)
        val inp2Val: String = document.getElementById(input2.idStr).asInstanceOf[html.Input].value
        val inp2Option: OptionHtml = input2.strToOption(inp2Val)
        val inp3Val: String = document.getElementById(input3.idStr).asInstanceOf[html.Input].value
        val inp4Val: String = document.getElementById(input4.idStr).asInstanceOf[html.Input].value
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption, inp2Option, inp3Val, inp4Val).out
      }
      case Callback2Opt2Str2Html(listenerId, input1, input3, input4, f) =>
      { val listener: Element = document.getElementById(listenerId)
        val inp1Val: String = document.getElementById(input1.idStr).asInstanceOf[html.Input].value
        val inp1Option: OptionHtml = input1.strToOption(inp1Val)
        val inp3Val: String = document.getElementById(input3.idStr).asInstanceOf[html.Input].value
        val inp4Val: String = document.getElementById(input4.idStr).asInstanceOf[html.Input].value
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(inp1Option, newOption, inp3Val, inp4Val).out
      }
      case CallbackOptInt1Html(listenerId, input2, f) =>
      { val listener: Element = document.getElementById(listenerId)
        val inp2Val: Int = document.getElementById(input2.idStr).asInstanceOf[html.Input].value.toInt
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption, inp2Val).out
      }
      case CallbackOptInt1Text(listenerId, input2, f) =>
      { val listener: Element = document.getElementById(listenerId)
        val inp2Val: Int = document.getElementById(input2.idStr).asInstanceOf[html.Input].value.toInt
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newOption, inp2Val)
      }
      case CallbackOptDbl1Html(listenerId, input2, f) => {
        val listener: Element = document.getElementById(listenerId)
        val inp2Val: Double = document.getElementById(input2.idStr).asInstanceOf[html.Input].value.toDouble
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.innerHTML = f(newOption, inp2Val).out
      }
      case CallbackOptDbl1Text(listenerId, input2, f) => {
        val listener: Element = document.getElementById(listenerId)
        val inp2Val: Double = document.getElementById(input2.idStr).asInstanceOf[html.Input].value.toDouble
        if(listener == null) deb(s" target is null from inputer $inputer for id: $listenerId.")
        else listener.textContent = f(newOption, inp2Val)
      }
    }
  }
}