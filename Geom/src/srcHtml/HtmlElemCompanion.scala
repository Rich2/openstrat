/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb;

/** Trait for companion objects of HTML elements, that adds listening methods. */
trait HtmlElemCompanion[T, CT <: XCon]
{ /** Utility method to allow many other factory methods to implemented in this super trait. */
  def fromStr(str: String, attribs: RArr[XAtt]): T

  /** Creates an HTML element of the given type and registers the textContent with an [[UpdaterSelect]]. */
  def listenOptText(input1: UpdaterSelect, otherAttribs: RArr[XAtt] = RArr())(f: OptionHtml => String): T =
  { val newId: IdAtt = input1.nextOptText(f)
    fromStr(f(input1.initOption), newId %: otherAttribs)
  }
  
  /** Creates an HTML element of the given type and listens to an [[UpdaterSelect]] and an [[UpdaterIntInput]] updating the textContent. */
  def listenOptIntText(input1: UpdaterSelect, input2: UpdaterIntInput, otherAttribs: RArr[XAtt] = RArr())(f: (OptionHtml, Int) => String): T =
  { val newId: IdAtt = input1.nextOptIntText1(input2, f)
    fromStr(f(input1.initOption, input2.value), newId %: otherAttribs)
  }

  /** Creates an HTML element of the given type and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptDblText(input1: UpdaterSelect, input2: UpdaterDblInput, otherAttribs: RArr[XAtt] = RArr())(f: (OptionHtml, Double) => String): T =
  { val newId: IdAtt = input1.nextOptDblText1(input2, f)
    fromStr(f(input1.initOption, input2.value), newId %: otherAttribs)
  }

  /** Creates an HTML element of the given type and registers the textContent with a String => String callback to the textContent. */
  def listenStrText(input: UpdaterStr, otherAttribs: RArr[XAtt] = RArr())(f: String => String): T =
  { val newId: IdAtt = input.nextStrText(f)
    fromStr(f(input.valueStr), newId %: otherAttribs)
  }

  /** Creates an HTML element of the given type and registers the textContent with a (String, String) => String function callback. */
  def listen2StrText(input1: UpdaterStr, input2: UpdaterStr, otherAttribs: RArr[XAtt] = RArr())(f: (String, String) => String): T =
  { val newId: IdAtt = input1.next2Str1Text(input2, f)
    fromStr(f(input1.valueStr, input2.valueStr), newId %: otherAttribs)
  }

  /** Creates  an HTML element of the given type and registers the textContent with a (String, String, String) => String callback. */
  def listen3StrText(input1: UpdaterStr, input2: UpdaterStr, input3: UpdaterStr, otherAttribs: RArr[XAtt] = RArr())(f: (String, String, String) => String): T =
  { val newId: IdAtt = input1.next3Str1Text(input2, input3, f)
    fromStr(f(input1.valueStr, input2.valueStr, input3.valueStr), newId %: otherAttribs)
  }

  /** Creates an HTML element of the given type and registers the textContent with an [[UpdaterStr]] and an [[UpdaterDblInput]]. */
  def listenStrDblText(input1: UpdaterStr, input2: UpdaterDblInput, otherAttribs: RArr[XAtt] = RArr())(f: (String, Double) => String): T =
  { val newId: IdAtt = input1.nextStrDbl1Text(input2, f)
    fromStr(f(input1.valueStr, input2.value), newId %: otherAttribs)
  }
  
  /** Creates an HTML element of the given type and registers the textContent with an [[UpdaterIntInput]]. */
  def listenIntText(input: UpdaterIntInput, otherAttribs: RArr[XAtt] = RArr())(f: Int => String): T ={
    val newId = input.next1(f)
    fromStr(f(input.value), newId %: otherAttribs)
  }

  /** Creates an HTML element of the given type and registers the textContent with an [[UpdaterDblInput]]. */
  def listenDblText(input: UpdaterDblInput, otherAttribs: RArr[XAtt] = RArr())(f: Double => String): T =
  { val newId: IdAtt = input.next1(f)
    fromStr(f(input.value), newId %: otherAttribs)
  }

  def apply(contents: RArr[CT], attribs: RArr[XAtt]): T

  def fRepeat: Seq[CT] => RArr[CT]

  /** Factory method to create an HTML element of the given type with an ID attribute. */
  def id(id: String, contents: CT*): T = apply(fRepeat(contents), RArr(IdAtt(id)))

  /** Creates an HTML element of the given type with a class attribute. */
  def classAtt(id: String, contents: CT*): T = apply(fRepeat(contents), RArr(ClassAtt(id)))

  /** Creates an HTML element of the given type and listens to an [[UpdaterSelect]] change events modifying the inner HTML. */
  def listenOptHtml(input: UpdaterSelect, otherAttribs: RArr[XAtt] = RArr())(f: OptionHtml => RArr[CT]): T =
  { val newId: IdAtt = input.nextOptHtml(f)
    apply(input.listenerInit(f), newId %: otherAttribs)
  }

  /** Creates an HTML element of the given type and registers the innerHTML with 2 [[UpdaterSelect]]s and 2 [[UpdaterStr]]s. */
  def listen2Opt2StrHtml(input1: UpdaterSelect, input2: UpdaterSelect, input3: UpdaterStr, input4: UpdaterStr, otherAttribs: RArr[XAtt] = RArr())(
    f: (OptionHtml, OptionHtml, String, String) => RArr[CT]): T =
  { val newId: IdAtt = input1.next2Opt2StrHtml(input2, input3, input4, f)
    apply(f(input1.initOption, input2.initOption, input3.valueStr, input4.valueStr), newId %: otherAttribs)
  }
  
  /** Creates an HTML element of the given type and registers the innerHTML an [[UpdaterSelect]] and an [[UpdaterIntInput]]. */
  def listenOptIntHtml(input1: UpdaterSelect, input2: UpdaterIntInput, otherAttribs: RArr[XAtt] = RArr())(f: (OptionHtml, Int) => RArr[CT]): T =
  { val newId: IdAtt = input1.nextOptInt1Html(input2, f)
    apply(f(input1.initOption, input2.value), newId %: otherAttribs)
  }

  /** Creates an HTML element of the given type and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptDblHtml(input1: UpdaterSelect, input2: UpdaterDblInput, otherAttribs: RArr[XAtt] = RArr())(f: (OptionHtml, Double) => RArr[CT]): T =
  { val newId: IdAtt = input1.nextOptDbl1Html(input2, f)
    apply(f(input1.initOption, input2.value), newId %: otherAttribs)
  }

  /** Creates an HTML element of the given type and registers with a [[UpdaterStr]]. Changes inner HTML on change event. */
  def listenStrHtml(input: UpdaterStr, otherAttribs: RArr[XAtt] = RArr())(f: String => RArr[CT]): T =
  { val newId = input.nextStrHtml(f)
    apply(f(input.valueStr), newId %: otherAttribs)
  }
}

/** Trait for companion objects of HTML elements, that take [[XCon]] content, that adds listening methods. */
trait HtmlXConCompanion[T] extends HtmlElemCompanion[T, XCon]
{  override def fRepeat: Seq[XCon] => RArr[XCon] = _.toRArr
}

/** Trait for companion objects of HTML elements, that take [[XConInedit]] content, that adds listening methods. */
trait HtmlIneditCompanion[T] extends HtmlElemCompanion[T, XConInedit]
{  override def fRepeat: Seq[XConInedit] => RArr[XConInedit] = _.toRArr
}