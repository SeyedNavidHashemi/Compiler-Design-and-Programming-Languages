int main():
    int a = 5
    int b = a++
    int c = ++a + b
    int d = c-- - a

    if (d > 0):
        d += a

    return 0
end
