/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** Creates for a "class" XML / HTML attribute." */
class ClassAtt(val valueStr: String) extends XAttShort, CssPartialSelector
{ ThisAtt =>
  override def name: String = "class"
  
  override def cssOut: String = "." + valueStr

  /** Constructs an HTML Div with this CSS class attribute. */
  def div(contents: XCon*): DivHtml = DivHtml(RArr(this), contents.toRArr)

  /** CSS rule for this CSS class. */
  //def rule(decs: CssDecBase*): ClassRule = ClassRule(this, decs.toRArr)
}

object ClassAtt
{ /** Factory apply method for HTML class attribute. */
  def apply(classStr: String): ClassAtt = new ClassAtt(classStr)
}

/** CSS class rule. */
/*trait CssClassRule extends CssRule
{
  def cssClass: ClassAtt

  /** The CSS name for the class. */
  def classStr: String = cssClass.valueStr

  override def selecStr: String = "." + classStr
  def child(childSel: SelSimpleOrStr, decsArr: RArr[CssDecBase]): CssChildRule = CssChildRule(selecStr, childSel, decsArr)
  def child(childSel: SelSimpleOrStr, decs: CssDecBase*): CssChildRule = CssChildRule(selecStr, childSel, decs.toRArr)
}*/

/*object CssClassRule
{ /** Factory apply method to construct a CSS class rule. There is an apply name overload that takes CSS declarations as repeat parameters. */
  def apply(cssClass: ClassAtt, decsArr: RArr[CssDecBase]): CssClassRule = CssClassRuleGen(cssClass, decsArr)

  /** Factory apply method to construct a CSS class rule. There is an apply name overload that takes the CSS declarations as sn [[RArr]]. */
  def apply(cssClass: ClassAtt, decs: CssDecBase*): CssClassRule = CssClassRuleGen(cssClass, decs.toRArr)

  /** implementation class for the general case of a CSS class rule. */
  case class CssClassRuleGen(cssClass: ClassAtt, decsArr: RArr[CssDecBase]) extends CssClassRule
}*/