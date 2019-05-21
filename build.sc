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
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'jvm / 'src)

  trait InnerJs extends ScalaJSModule with Common
  { 
  	def scalaVersion = "2.12.8"
	  def scalaJSVersion = "0.6.27" 
	  def sources = T.sources(outer.millSourcePath / 'src, millSourcePath / 'src)
	  
	  def ivyDeps = outer.ivyDeps() ++  Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.6")
  }

  trait InnerNative extends ScalaNativeModule with Common
  {
    def scalaVersion = "2.11.12"
    def scalaNativeVersion = "0.3.8"  
	  def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcNat)
	  def ivyDeps = outer.ivyDeps() //++ ivyNat()	 
  }

  trait InnerTests extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.6")
    def testFrameworks = Seq("utest.runner.Framework") 
    def sources = T.sources(millSourcePath / 'src)  
  }
}

object UtilMacros extends PlatformsModule
  {
    def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
    object js extends InnerJs
    object Nat extends InnerNative  
  }

object Util extends PlatformsModule
{ 
  def moduleDeps = Seq(UtilMacros)  
  object test extends InnerTests  
  object js extends InnerJs {  def moduleDeps = Seq(UtilMacros.js)  }
  object Nat extends InnerNative  
}

object Graphic extends PlatformsModule
{ 
  def moduleDeps = Seq(Util)
  
  object test extends InnerTests
  
  object js extends InnerJs {  def moduleDeps = Seq(Util.js)  }
  object Nat extends InnerNative
}

object Strat extends PlatformsModule
{
   def moduleDeps = Seq(Graphic)   
  
  object test extends InnerTests
  {  //def moduleDeps = Seq(Graphic.test, Core)   
  }
  
  object js extends InnerJs { def moduleDeps = Seq(Graphic.js) }
  object Nat extends InnerNative
}

object DevModule extends PlatformsModule
{
  def moduleDeps = Seq(Strat)
  def mainClass = Some("ostrat.pFx.DevApp")
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'jvm / 'src, millSourcePath / 'srcLearn)
}

def run() = DevModule.runBackground()
def test = Util.test
def jsfast = Strat.js.fastOpt
def jsfull = Strat.js.fullOpt
