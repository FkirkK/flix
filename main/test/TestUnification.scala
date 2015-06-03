import impl.logic.{Symbol, Term, Type, Value}
import impl.runtime.Unification
import org.scalatest.FunSuite

class TestUnification extends FunSuite {

  val x = Symbol.VariableSymbol("x")
  val y = Symbol.VariableSymbol("y")
  val z = Symbol.VariableSymbol("z")


//  P(t1, t2, t3)
//    [4:17:14 PM] Ondrej Lhotak: (rule (Nodes n) ((CFG n {_})))
//  [4:36:51 PM] Magnus Madsen: Set { "a", y }
//  [4:37:11 PM] Magnus Madsen: Set { "a", "b", "z" }
//  [4:38:01 PM] Magnus Madsen: Set { x, y }
//  [4:40:08 PM] Ondrej Lhotak: Set {x}, Set{y}
//    [4:40:27 PM] Ondrej Lhotak: Set{x,y}
//  [4:40:39 PM] Magnus Madsen: Membership x, Membership y
//  [4:40:56 PM] Ondrej Lhotak: x \in Set
//  [4:41:05 PM] Ondrej Lhotak: ElementOf(x, Set)
//  [4:41:47 PM] Ondrej Lhotak: Set{_,_}
//  [4:42:37 PM] Magnus Madsen: Set {x, y, z} unify with Set {"a", "b"}
//    [4:43:57 PM] Ondrej Lhotak: Set{_,_,_}
//  [4:44:27 PM] Magnus Madsen: And Set {x, x}
//  [4:45:22 PM | Edited 4:45:33 PM] Ondrej Lhotak: Set{x,y,z} unify with Set{"a","b","c"}
//    [4:46:38 PM] Magnus Madsen: Set {"a"} unify with Set {"a", "b", "c"}
//    [4:47:21 PM] Ondrej Lhotak: Set{x} unify with Set{"a","b","c"}
//    [4:47:28 PM] Ondrej Lhotak: x = a, x = b, x=c

  // unification properties?

  test("Unification.Success.01") {
    val t = Term.Unit
    val v = Value.Unit
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.02") {
    val t = Term.Bool(true)
    val v = Value.Bool(true)
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.03") {
    val t = Term.Bool(false)
    val v = Value.Bool(false)
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.04") {
    val t = Term.Int(42)
    val v = Value.Int(42)
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.05") {
    val t = Term.Str("foo")
    val v = Value.Str("foo")
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.06") {
    val t = Term.Tuple2(Term.Int(1), Term.Str("one"))
    val v = Value.Tuple2(Value.Int(1), Value.Str("one"))
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.07") {
    val typ = Type.Sum(List(Type.Tag(Symbol.NamedSymbol("Foo"), Type.Int)))
    val t = Term.Tag(Symbol.NamedSymbol("Foo"), Term.Int(42), typ)
    val v = Value.Tag(Symbol.NamedSymbol("Foo"), Value.Int(42), typ)
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.08") {
    val t = Term.Set(Set.empty)
    val v = Value.Set(Set.empty)
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.09") {
    val t = Term.Set(Set(Term.Unit))
    val v = Value.Set(Set(Value.Unit))
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.10") {
    val t = Term.Set(Set(Term.Bool(true), Term.Int(42)))
    val v = Value.Set(Set(Value.Bool(true), Value.Int(42)))
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Success.11") {
    val t = Term.Set(Set(Term.Set(Set(Term.Unit))))
    val v = Value.Set(Set(Value.Set(Set(Value.Unit))))
    val r = Unification.unify(t, v)
    assertResult(List(Map.empty))(r)
  }

  test("Unification.Failure.01") {
    val t = Term.Unit
    val v = Value.Bool(true)
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.02") {
    val t = Term.Bool(true)
    val v = Value.Bool(false)
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.03") {
    val t = Term.Bool(true)
    val v = Value.Int(42)
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.04") {
    val t = Term.Int(1)
    val v = Value.Int(2)
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.05") {
    val t = Term.Str("foo")
    val v = Value.Str("bar")
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.06") {
    val t = Term.Tuple2(Term.Int(1), Term.Str("one"))
    val v = Value.Tuple2(Value.Str("one"), Value.Int(1))
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.07") {
    val typ = Type.Sum(List(Type.Tag(Symbol.NamedSymbol("Foo"), Type.Int)))
    val t = Term.Tag(Symbol.NamedSymbol("Foo"), Term.Int(42), typ)
    val v = Value.Tag(Symbol.NamedSymbol("Bar"), Value.Int(42), typ)
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.08") {
    val t = Term.Set(Set(Term.Int(42)))
    val v = Value.Set(Set.empty)
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.09") {
    val t = Term.Set(Set(Term.Unit))
    val v = Value.Set(Set(Value.Int(42)))
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.10") {
    val t = Term.Set(Set(Term.Unit, Term.Int(42)))
    val v = Value.Set(Set(Value.Unit))
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Failure.11") {
    val t = Term.Set(Set(Term.Int(1), Term.Int(2), Term.Int(3)))
    val v = Value.Set(Set(Value.Int(1)))
    val r = Unification.unify(t, v)
    assertResult(List.empty)(r)
  }

  test("Unification.Match.01") {
    val t = Term.Var(x)
    val v = Value.Unit
    val r = Unification.unify(t, v)
    assertResult(List(Map(x -> Value.Unit)))(r)
  }

  test("Unification.Match.02") {
    val t = Term.Var(x)
    val v = Value.Bool(true)
    val r = Unification.unify(t, v)
    assertResult(List(Map(x -> Value.Bool(true))))(r)
  }

  test("Unification.Match.03") {
    val t = Term.Var(x)
    val v = Value.Int(42)
    val r = Unification.unify(t, v)
    assertResult(List(Map(x -> Value.Int(42))))(r)
  }

  test("Unification.Match.04") {
    val t = Term.Var(x)
    val v = Value.Tuple2(Value.Int(1), Value.Int(2))
    val r = Unification.unify(t, v)
    assertResult(List(Map(x -> Value.Tuple2(Value.Int(1), Value.Int(2)))))(r)
  }

  test("Unification.Match.05") {
    val t = Term.Tuple2(Term.Var(x), Term.Var(y))
    val v = Value.Tuple2(Value.Int(1), Value.Int(2))
    val r = Unification.unify(t, v)
    assertResult(List(Map(x -> Value.Int(1), y -> Value.Int(2))))(r)
  }

  test("Unification.Match.06") {
    val t = Term.Tuple2(Term.Var(x), Term.Var(x))
    val v = Value.Tuple2(Value.Int(42), Value.Int(42))
    val r = Unification.unify(t, v)
    assertResult(List(Map(x -> Value.Int(42))))(r)
  }

  test("Unification.Match.07") {
    val t = Term.Set(Set(Term.Var(x)))
    val v = Value.Set(Set(Value.Int(1), Value.Int(2), Value.Int(3)))
    val r = Unification.unify(t, v)
    assertResult(List(
      Map(x -> Value.Int(1)),
      Map(x -> Value.Int(2)),
      Map(x -> Value.Int(3))
    ))(r)
  }

  test("Unification.Match.08") {
    val t = Term.Set(Set(Term.Int(1), Term.Var(x), Term.Int(3)))
    val v = Value.Set(Set(Value.Int(1), Value.Int(2), Value.Int(3)))
    val r = Unification.unify(t, v)
    assertResult(List(
      Map(x -> Value.Int(2))
    ))(r)
  }

  test("Unification.Match.09") {
    val t = Term.Set(Set(Term.Var(x), Term.Var(y)))
    val v = Value.Set(Set(Value.Int(1), Value.Int(2)))
    val r = Unification.unify(t, v)
    assertResult(List(
      Map(x -> Value.Int(1), y -> Value.Int(2)),
      Map(x -> Value.Int(2), y -> Value.Int(1))
    ))(r)
  }
}