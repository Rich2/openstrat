/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** HTML Select element that updates other parts of the page on changed input. */
class UpdaterSelect(val idStr: String, val contents: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater) extends
  UpdaterInputLike(page), SelectHtml
{
  /** List of call backs to other parts of the web page that needed to be updated in response to new input. */
  var callBacks: RArr[CallbackOption] = RArr()
  
  /** This method sets the original HTML in the listener element from the first value in the Select list. */
  def listenerInit[CT <: XCon](f: OptionHtml => RArr[CT]): RArr[CT] = f(contents(0))
  
  /** This is a method for the JavaScript to recover the [[OptionHtml]] class from the [[String]]. */
  def strToOption(valStr: String): OptionHtml = contents.find(_.valueStr == valStr).getOrElse(OptionNotFound)

  /** The default value for this HTML Select element. */
  def initOption: OptionHtml = contents.headElse(OptionNotFound)

  /** Registers a page HTML element with this [[UpdaterSelect]]. Returns new unique id attribute to the listener. Takes String => RArr[XCon] function to update
   * the listener's htmlContent JavaScript method.. */
  def nextOptHtml(f: OptionHtml => RArr[XCon]): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallbackOptHtml(newListenerId, f)
    IdAtt(newListenerId)
  }

  /** Registers an HTML element with this [[UpdaterSelect]]. Returns new unique id attribute to the listener. Takes String => String function to update the
   * listener's textContent JavaScript method. */
  def nextOptText(f: OptionHtml => String): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallbackOptText(newListenerId, f)
    IdAtt(newListenerId)
  }

  /** Registers an HTML element with this [[updaterOption]] and 2 [[UpdaterStr]]s. Returns an id for the listening element. This takes an (OptionHtml,
   * OptionHtml, String, String) => RArr[XCon] function to update the innerHTML of the listener element. */
  def nextOpt2StrHtml(input2: UpdaterStr, input3: UpdaterStr, f: (OptionHtml, String, String) => RArr[XCon]): IdAtt ={
    val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallbackOpt2Str1Html(newListenerId, input2, input3, f)
    input2.nextOpt2Str2Html(newListenerId, this, input3, f)
    input3.nextOpt2Str3Html(newListenerId, this, input2, f)
    IdAtt(newListenerId)
  }

  /** Registers an HTML element with this [[updaterOption]] and 2 [[UpdaterStr]]s. Returns an id for the listening element. This takes an (OptionHtml,
   * OptionHtml, String, String) => RArr[XCon] function to update the innerHTML of the listener element. */
  def next2Opt2StrHtml(input2: UpdaterSelect, input3: UpdaterStr, input4: UpdaterStr, f: (OptionHtml, OptionHtml, String, String) => RArr[XCon]): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= Callback2Opt2Str1Html(newListenerId, input2, input3, input4, f)
    input2.next2Opt2Str2Html(newListenerId, this, input3, input4, f)
    input3.next2Opt2Str3Html(newListenerId, this, input2, input4, f)
    input4.next2Opt2Str4Html(newListenerId, this, input2, input3, f)
    IdAtt(newListenerId)
  }

  def next2Opt2Str2Html(listenerId: String, input1: UpdaterSelect, input3: UpdaterStr, input4: UpdaterStr,
    f: (OptionHtml, OptionHtml, String, String) => RArr[XCon]): Unit =
  { callBacks +%= Callback2Opt2Str2Html(listenerId, input1, input3, input4, f)
  }
  
  /** Registers an HTML element with this [[updaterOption]] and a [[UpdaterIntInput]]. Returns an id for the listening element. This takes an (OptionHtml, Int)
   * => RArr[XCon] function to update the innerHTML of the listener element. */
  def nextOptInt1Html(input2: UpdaterIntInput, f: (OptionHtml, Int) => RArr[XCon]): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallbackOptInt1Html(newListenerId, input2, f)
    input2.nextOptInt2Html(newListenerId, this, f)
    IdAtt(newListenerId)
  }

  /** Registers an HTML element with this [[UpdaterSelect]] and an [[UpdaterIntInput]]. Returns an id for the listener element. This takes an (OptionHtml, Int)
   * => RArr[XCon] function to update the textContent of the listener element. */
  def nextOptIntText1(input2: UpdaterIntInput, f: (OptionHtml, Int) => String): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallbackOptInt1Text(newListenerId, input2, f)
    input2.nextOptInt2Text(newListenerId, this, f)
    IdAtt(newListenerId)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a function of two [[String]] parameters,
   * the first from this text input and the second from another text updater, to update the target content. */
  def nextOptDbl1Html(input2: UpdaterDblInput, f: (OptionHtml, Double) => RArr[XCon]): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallbackOptDbl1Html(newListenerId, input2, f)
    input2.nextOptDbl2Html(newListenerId, this, f)
    IdAtt(newListenerId)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a function of two [[String]] parameters,
   * the first from this text input and the second from another text updater, to update the target content. */
  def nextOptDblText1(input2: UpdaterDblInput, f: (OptionHtml, Double) => String): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallbackOptDbl1Text(newListenerId, input2, f)
    input2.nextOptDbl2Text(newListenerId, this, f)
    IdAtt(newListenerId)
  }

  override def numListeners: Int = callBacks.length
}

object UpdaterSelect
{ /** Factory apply method to construct HTML Select element that updates page through JavaScript. */
  def apply(idStr: String, options: RArr[OptionHtml], visNum: Int, otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater): UpdaterSelect =
    new UpdaterSelect(idStr, options, visNum, otherAttribs)

  /** Factory apply method to construct HTML Select element that updates page through JavaScript. */
  def apply(idStr: String, options: OptionHtml*)(using page: PageHtmlUpdater): UpdaterSelect =
    new UpdaterSelect(idStr, options.toRArr, 1, RArr())
}