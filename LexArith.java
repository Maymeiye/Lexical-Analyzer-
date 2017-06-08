// Mei Ye 23459517

public abstract class LexArith extends IO
{
	public enum State 
    { 
		//I was added additional states:
		//non-final states:Ba, Ampersand
		//final states:LBrace, RBrace, Colon, Semicolon, Or, And, Gt, Ge, Lt, Le, Assign, Eq, Inv, Neq,
		
		//non-final states
		Start, Period, E, EPlusMinus, Bar, Ampersand,
		// final states
		Id, Int, Float, FloatE, Add, Minus, Times, 
		Div, LParen, RParen, LBrace, RBrace, Colon, 
		Semicolon, Or, And, Gt, Ge, Lt, Le, Assign,
		Eq, Inv, Neq, UNDEF;

		private boolean isFinal()
		{
			return ( this.compareTo(State.Id) >= 0 );  
		}	
	}

	public static String t; 
	public static State state; 
	
	private static int driver()
	{
		State nextSt; 
		t = "";
		state = State.Start;

		if ( Character.isWhitespace((char) a) )
			a = getChar(); 
		if ( a == -1 ) 
			return -1;

		while ( a != -1 ) 
		{
			c = (char) a;
			nextSt = nextState( state, c );
			if ( nextSt == State.UNDEF ) 
			{
				if ( state.isFinal() )
					return 1; 
				else 
				{
					t = t + c;
					a = getNextChar();
					return 0; 
				}
			}
			else 
			{
				state = nextSt;
				t = t + c;
				a = getNextChar();
			}
		}

		if ( state.isFinal() )
			return 1; 
		else
			return 0; 
	} 

	public static void getToken()
	{
		int i = driver();
		if ( i == 0 )
			displayln(t + " : Lexical Error, invalid token");
	}

	private static State nextState(State s, char c)
	{
		switch( state )
		{
		case Start:
			if ( Character.isLetter(c) )
				return State.Id;
			else if ( Character.isDigit(c) )
				return State.Int;
			else if ( c == '+' )
				return State.Add;
			else if ( c == '-' )
				return State.Minus;
			else if ( c == '*' )
				return State.Times;
			else if ( c == '/' )
				return State.Div;
			else if ( c == '(' )
				return State.LParen;
			else if ( c == ')' )
				return State.RParen;
//additional transition state I wrote: 
			else if ( c == '{' )
				return State.LBrace;
			else if ( c == '}' )
				return State.RBrace;
			else if ( c == ':' )
				return State.Colon;
			else if ( c == ';' )
				return State.Semicolon;
			else if ( c == '.' )
				return State.Period;
			else if ( c == '|' )
				return State.Bar;
			else if ( c == '&' )
				return State.Ampersand;
			else if ( c == '>' )
				return State.Gt;
			else if ( c == '<' )
				return State.Lt;
			else if ( c == '=' )
				return State.Assign;
			else if ( c == '!' )
				return State.Inv;
			else
				return State.UNDEF;
		case Id:
			if ( Character.isLetterOrDigit(c) )
				return State.Id;
			else
				return State.UNDEF;
		case Int:
			if ( Character.isDigit(c) )
				return State.Int;
			else if ( c == '.' )
				return State.Period;
			else
				return State.UNDEF;
		case Period:
			if ( Character.isDigit(c) )
				return State.Float;
			else if ( c == 'e' || c == 'E' )
				return State.E;
			else if(Character.isWhitespace(c))
				return State.Float;
			else
				return State.UNDEF;
		case Float:
			if ( Character.isDigit(c) )
				return State.Float;
			else if ( c == 'e' || c == 'E' )
				return State.E;
			else
				return State.UNDEF;
		case E:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else if ( c == '+' || c == '-' )
				return State.EPlusMinus;
			else
				return State.UNDEF;
		case EPlusMinus:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else
				return State.UNDEF;
		case FloatE:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else
				return State.UNDEF;
//additional transition state I wrote: 		
		case Bar:
			if(c == '|')
				return State.Or;
			else 
				return State.UNDEF;
		case Ampersand:
			if(c == '&')
				return State.And;
			else 
				return State.UNDEF;
		case Gt:
			if(c == '=')
				return State.Ge;
			else 
				return State.UNDEF;	
		case Lt:
			if(c == '=')
				return State.Le;
			else 
				return State.UNDEF;	
		case Assign:
			if(c == '=')
				return State.Eq;
			else 
				return State.UNDEF;
		case Inv:
			if(c == '=')
				return State.Neq;
			else 
				return State.UNDEF;
		default:
			return State.UNDEF;
		}
	} 

	public static void main(String argv[])
	{		
		setIO( argv[0], argv[1] );
		
		int i;

		while ( a != -1 ) 
		{
			i = driver(); 
			if ( i == 1 )
			{
				if(state == State.Id)
				{
				//I stored 11 keywords into and array, when the state goto Id State, compare each token with keyword
				// if they match print it is a keyword
				// otherwise print this token is Id
					String[] keyword ={"if", "else", "switch", "case", "default", "while", "do", "for", "print", "false", "true"};
					boolean check = false;
					for(int j = 0; j < 11; j++)
					{
						if(t.equals( keyword[j] ))
						{
							displayln( t + "   : " + "Keyword_" + t );
							check = true;
							break;
						}
						
					}
					if (!check) displayln( t + "   : " + state.toString() );
				
				}
			    else
					displayln( t + "   : " + state.toString() );
			}		
			else if ( i == 0 )
				displayln( t + " : Lexical Error, invalid token");
		} 
		closeIO();
	}
} 
