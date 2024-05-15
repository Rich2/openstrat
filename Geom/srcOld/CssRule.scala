/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWeb

object CssRule
{
   def r(selectorIn: String, cssDecsIn: CssDec*): CssRule = new CssRule
   {
      val selector = selectorIn
      val cssDecs = cssDecsIn
   }
   def apply(selectorIn: String, cssDecsIn: Seq[CssDec]): CssRule = new CssRule
   {
      val selector = selectorIn
      val cssDecs = cssDecsIn
   }
}

trait CssRule extends IndentCon
{
   def selector: String
   val cssDecs: Seq[CssDec]
   override def out(indent: Int): String = selector -- "{" + cssDecs.out(indent + 2) + "}"
   override def multiLine = true
}

object CssClassesRule 
{
   def apply(firstClassNameIn: String, otherClassNamesIn: String*)(cssDecsIn: CssDec*): CssClassesRule = new CssClassesRule
   {
      override val firstClassName = firstClassNameIn
      override val otherClassNames = otherClassNamesIn
      override val cssDecs = cssDecsIn
   }
}
trait CssClassesRule extends CssRule
{
   def firstClassName: String
   def otherClassNames: Seq[String]
   final override def selector = (firstClassName +: otherClassNames).map("." + _).commaFold
}

object CssClassRule 
{
   def apply(classNameIn: String, cssDecsIn: CssDec*): CssClassRule = new CssClassRule
   {
      override val className = classNameIn      
      override val cssDecs = cssDecsIn
   }
}
trait CssClassRule extends CssRule
{
   def className: String   
   final override def selector = "." + className
}

object CssP
{
   def apply(cssDecsIn: CssDec*): CssP = new CssP
   {      
      val cssDecs = cssDecsIn
   }
}
trait CssP extends CssRule{ def selector = "p" }

object CssImg
{
   def apply(cssDecsIn: CssDec*): CssImg = new CssImg
   {      
      val cssDecs = cssDecsIn
   }
}
trait CssImg extends CssRule{ def selector = "img" }
