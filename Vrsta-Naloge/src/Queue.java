class QueueElement
{
	Object element;
	QueueElement next;

	QueueElement()
	{
		element = null;
		next = null;
	}
}

class Queue
{
	//QueueElement -> QueueElement -> QueueElement -> ... -> QueueElement
	//     ^                                                       ^
	//     |                                                       |  
	//    front                                                   rear
	//
	// nove elemente dodajamo na konec vrste (kazalec rear)
	// elemente brisemo na zacetku vrste (kazalec front)
	
	private QueueElement front;
	private QueueElement rear;
	
	public Queue()
	{
		makenull();
	}
	
	public void makenull()
	{
		front = null;
		rear = null;
	}
	
	public boolean empty()
	{
		return (front == null);
	}
	
	public Object front()
	{
		// funkcija vrne zacetni element vrste (nanj kaze kazalec front).
		// Elementa NE ODSTRANIMO iz vrste!
		
		return front.element;
	}
	
	public void enqueue(Object obj)
	{
		if (rear == null) {
			front = new QueueElement();
			front.element = obj;
			front.next = null;
			rear = front;
			return;
		}
		rear.next = new QueueElement();
		rear.next.element = obj;
		rear.next.next = null;
		rear = rear.next;
		// funkcija doda element na konec vrste (nanj kaze kazalec rear)
	}
	
	public void dequeue()
	{
		if (front == null) {
			return;
		}
		
		if (front == rear) {
			front = null;
			rear = null;
			return;
		}
		// System.out.println("Front " + (Integer)front.element + " Front next " + (Integer)front.next.element);
		front = front.next;

		// funkcija odstrani zacetni element vrste (nanj kaze kazalec front)
	}

	public void print() {
		QueueElement el = front;

		System.out.print("Queue: ");
		while (el != rear.next) {
			System.out.print(el.element + ", ");
			el = el.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Queue queue = new Queue();

		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);

		System.out.println((Integer)queue.front());
		queue.dequeue();
		System.out.println((Integer)queue.front());
		queue.dequeue();
		System.out.println((Integer)queue.front());
	}
}
