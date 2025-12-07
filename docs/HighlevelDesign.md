# Data structure
We will have span
it will have execId that will be used to identify the execution
Then we will have prevSpanId,
PrevSpanId is used to recognize the previous span that happended
Then we will have parentSpanId,
ParentSpanId is used to recognize the parent span.
Children span represent the internals of the span 

An example of this is 

ServiceA calling service B

stuff that happend in serviceA will be in one span
next span will be serviceB

then the children span of serviceB will be the internals of serviceB
and the children span of serviceA will be the internals of serviceA

they will also have its own prev span ids and parent span ids