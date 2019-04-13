// build.sc
import mill._, scalalib._, scalajslib._, scalanativelib._

trait Common extends ScalaModule
{
  def version = "0.0.4snap"  
  def scalacOptions = Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint")
}

trait PlatformsModule extends ScalaModule with Common
{
  outer =>
  
  def scalaVersion = "2.12.8"
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm)

  trait InnerJs extends ScalaJSModule with Common
  { 
  	def scalaVersion = "2.12.8"
	  def scalaJSVersion = "0.6.26" 
	  def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcJs)
	  
	  def ivyDeps = outer.ivyDeps() ++  Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.6")
	  
	  //def ivyJs = Agg()
  }

  trait InnerNative extends ScalaNativeModule with Common
  {
    def scalaVersion = "2.11.12"
    def scalaNativeVersion = "0.3.8"  
	  def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcNat)
	  def ivyDeps = outer.ivyDeps() //++ ivyNat()
	  //def ivyNat = Agg()
  }

  trait InnerTests extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.6")
    def testFrameworks = Seq("utest.runner.Framework") 
     def sources = T.sources(outer.millSourcePath / 'srcTest)  
  }
}

object Util extends PlatformsModule
{ 
  def moduleDeps = Seq(Macros)  
  object test extends InnerTests  
  object Js extends InnerJs {  def moduleDeps = Seq(Macros.Js)  }
  object Nat extends InnerNative

  object Macros extends PlatformsModule
  {
    def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
    object Js extends InnerJs
    object Nat extends InnerNative  
  }
}

object Graphic extends PlatformsModule
{ 
  def moduleDeps = Seq(Util)
  
  object test extends InnerTests 
  object Js extends InnerJs {  def moduleDeps = Seq(Util.Js)  }
  object Nat extends InnerNative
}

object Strat extends PlatformsModule
{
   def moduleDeps = Seq(Graphic)
   def mainClass = Some("ostrat.pFx.DevApp")
  
  object test extends InnerTests
  {  //def moduleDeps = Seq(Graphic.test, Core)   
  }
  
  object Js extends InnerJs { def moduleDeps = Seq(Graphic.Js) }
  object Nat extends InnerNative
}

object Learn extends PlatformsModule
{
  def moduleDeps = Seq(Strat)
}

def run() = Strat.runBackground()
def test = Strat.test
def jsfast = Strat.Js.fastOpt
def jsfull = Strat.Js.fullOpt
