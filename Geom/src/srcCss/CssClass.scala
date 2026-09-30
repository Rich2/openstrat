/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** Creates for a "class" XML / HTML attribute." */
class ClassAtt(val valueStr: String) extends XAttShort, CssPartialSelector
{ ThisAtt =>
  override def name: String = "class"
  
  override def cssOut: String = "." + valueStr

  /** Constructs an HTML Div with this CSS class attribute. */
  def div(contents: XCon*): DivHtml = DivHtml(RArr(this), contents.toRArr)
}

object ClassAtt
{ /** Factory apply method for HTML class attribute. */
  def apply(classStr: String): ClassAtt = new ClassAtt(classStr)
}