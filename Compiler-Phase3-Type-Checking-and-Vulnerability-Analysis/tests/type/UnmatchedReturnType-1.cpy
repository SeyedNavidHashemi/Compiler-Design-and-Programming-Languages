double returnDoubleAsInt():
    return 42
end

int returnIntAsDouble():
    return 3.14
end

int main():
    double d = returnDoubleAsInt()
    int i = returnIntAsDouble()
    return 0
end