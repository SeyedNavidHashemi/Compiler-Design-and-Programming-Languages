void takeInt(int x):
    printf("%d\n", x)
end

int main():
    short s = 10
    char c = 'A'
    double d = 42.42
    
    takeInt(s)
    takeInt(c)
    takeInt(d)

    return 0
end